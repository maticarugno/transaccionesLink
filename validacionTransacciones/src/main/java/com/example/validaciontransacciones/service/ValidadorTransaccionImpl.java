package com.example.validaciontransacciones.service;

import com.example.validaciontransacciones.enums.TipoValidacion;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.commons.dto.Transaccion;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static org.example.commons.enums.Estados.COMPLETED;

@Service
@Slf4j
@AllArgsConstructor
public class ValidadorTransaccionImpl implements ValidadorTransaccion {

    private final TransaccionSender transaccionSender;

    @Override
    @RabbitListener(queues = "transacciones_a_validar")
    public void validar(Transaccion transaccion) {
        log.info("Transaccion recibida: {}", transaccion.getTransaccionId());
        try {
            TipoValidacion validacion = TipoValidacion.fromString(transaccion.getTipo());
            validacion.getValidacion().validar(transaccion);
        } catch (IllegalArgumentException e) {
            log.info("No hay validaciones que realizar sobre la transaccion");
            transaccion.setEstado(COMPLETED);
        }
        transaccionSender.enviarTransaccionValidada(transaccion);
    }

}
