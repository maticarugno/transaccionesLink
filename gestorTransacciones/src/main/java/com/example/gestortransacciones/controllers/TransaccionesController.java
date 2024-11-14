package com.example.gestortransacciones.controllers;

import com.example.gestortransacciones.service.GestorTransaccion;
import lombok.AllArgsConstructor;
import org.example.commons.dto.Transaccion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class TransaccionesController implements TransaccionesApi{

    private final GestorTransaccion gestorTransaccion;

    @Override
    public ResponseEntity<String> iniciarTransaccion(Transaccion transaccion) {
        gestorTransaccion.procesarTransaccion(transaccion);
        return ResponseEntity.ok("Transaccion Iniciada");
    }

    @Override
    public ResponseEntity<String> consultarEstadoTransaccion(Long transaccionId) {
        return ResponseEntity.ok(gestorTransaccion.getTransaccionEstado(transaccionId));
    }

    @Override
    public ResponseEntity<List<Transaccion>> listarTransacciones(Long usuarioId, Map<String, Object> filtros, Long montoMin, Long montoMax, LocalDateTime fechaDesde, LocalDateTime fechaHasta, String sortBy, String orden) {
        return ResponseEntity.ok(gestorTransaccion.getAllTransaccionesBy(usuarioId, filtros, montoMin, montoMax,fechaDesde,fechaHasta,sortBy,orden));
    }

}
