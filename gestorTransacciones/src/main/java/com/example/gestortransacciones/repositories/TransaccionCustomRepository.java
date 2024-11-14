package com.example.gestortransacciones.repositories;

import org.example.commons.dto.Transaccion;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface TransaccionCustomRepository {
    List<Transaccion> findAllTransaccionesBy(Long usuarioId, Map<String, Object> filtros, Long montoMin, Long montoMax, LocalDateTime fechaDesde, LocalDateTime fechaHasta, String sortBy, String orden);
}
