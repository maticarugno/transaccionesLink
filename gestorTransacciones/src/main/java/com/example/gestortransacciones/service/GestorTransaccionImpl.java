package com.example.gestortransacciones.service;

import com.example.gestortransacciones.exceptions.DataNotFoundException;
import com.example.gestortransacciones.repositories.TransaccionRepository;
import com.example.gestortransacciones.service.queues.TransaccionSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.commons.dto.Transaccion;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.example.commons.enums.Estados.FAILED;
import static org.example.commons.enums.Estados.PENDING;

@AllArgsConstructor
@Service
@Slf4j
public class GestorTransaccionImpl implements GestorTransaccion {

    private WebClient webClient;
    private final TransaccionRepository transaccionRepository;
    private final TransaccionSender transaccionSender;

    @Override
    public String test() {
        return webClient.get().uri("http://localhost:8081/conversor").retrieve().bodyToMono(String.class).block();
    }

    @Override
    public void procesarTransaccion(Transaccion transaccion) {
        //validar que la transaccion no exista
        if (transaccionRepository.getByTransaccionId(transaccion.getTransaccionId()) != null) {
            //si ya existe no hago nada, no la guardo pero tampoco lanzo error
            return;
        }

        inicializarTransaccion(transaccion);
        transaccionRepository.save(transaccion);

        //se envia la transaccion al microservicio de validacion
        transaccionSender.enviarTransaccionParaValidar(transaccion);
    }

    @Override
    public void procesarTransaccionValidada(Transaccion transaccion) {
        transaccionRepository.save(transaccion);
        if (transaccion.getEstado().equals(FAILED)) {
            //enviar al servicio de compensacion
            transaccionSender.enviarTransaccionParaCompensacion(transaccion);
        }
    }

    @Override
    public String getTransaccionEstado(Long transaccionId) {
        Transaccion transaccion = transaccionRepository.getByTransaccionId(transaccionId);
        if (transaccion == null) {
            throw new DataNotFoundException("No se encontro la transaccion " + transaccionId);
        }
        return transaccion.getEstado().name();
    }

    @Override
    public List<Transaccion> getAllTransaccionesBy(Long usuarioId, Map<String, Object> filtros, Long montoMin, Long montoMax, LocalDateTime fechaDesde, LocalDateTime fechaHasta, String sortBy, String orden) {
        return transaccionRepository.findAllTransaccionesBy(usuarioId, filtros, montoMin, montoMax, fechaDesde, fechaHasta, sortBy, orden);
    }

    private void inicializarTransaccion(Transaccion transaccion) {
        transaccion.setEstado(PENDING);
        try{
            transaccion.setMontoMonedaBase(getMontoMonedaBase(transaccion.getMonto(), transaccion.getMoneda()));
        } catch (WebClientResponseException ex){
            log.error(ex.getResponseBodyAsString());
        }
    }

    private Long getMontoMonedaBase(Long monto, String moneda) {
        webClient = WebClient.builder().baseUrl("http://localhost:8081").build();
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/conversor/convertirAMonedaBase")
                        .queryParam("monto", monto.toString())
                        .queryParam("moneda", moneda)
                        .build())
                .retrieve()
                .bodyToMono(Long.class)
                .block();
    }
}
