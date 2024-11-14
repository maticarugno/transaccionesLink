package com.example.gestortransacciones.service;


import org.example.commons.dto.Transaccion;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface GestorTransaccion {
    void procesarTransaccion(Transaccion transaccion);
    void procesarTransaccionValidada(Transaccion transaccion);
    String getTransaccionEstado(Long transaccionId);
    List<Transaccion> getAllTransaccionesBy(Long usuarioId, Map<String, Object> filtros, Long montoMin, Long montoMax, LocalDateTime fechaDesde, LocalDateTime fechaHasta, String sortBy, String orden);
}
