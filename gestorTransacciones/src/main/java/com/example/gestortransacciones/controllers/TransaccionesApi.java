package com.example.gestortransacciones.controllers;

import org.example.commons.dto.Transaccion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("transacciones")
public interface TransaccionesApi {

    @PostMapping
    ResponseEntity<String> iniciarTransaccion(@RequestBody Transaccion transaccion);

    @GetMapping("/{transaccionId}/estado")
    ResponseEntity<String> consultarEstadoTransaccion(@PathVariable Long transaccionId);

    @GetMapping("/usuario/{usuarioId}")
    ResponseEntity<List<Transaccion>> listarTransacciones(
            @PathVariable Long usuarioId,
            @RequestParam Map<String, Object> filtros,
            @RequestParam(required = false) Long montoMin,
            @RequestParam(required = false) Long montoMax,
            @RequestParam(required = false) LocalDateTime fechaDesde,
            @RequestParam(required = false) LocalDateTime fechaHasta,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "asc") String orden);
}
