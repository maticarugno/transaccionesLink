package com.example.gestortransacciones.repositories;

import lombok.AllArgsConstructor;
import org.example.commons.dto.Transaccion;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class TransaccionCustomRepositoryImpl implements TransaccionCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Transaccion> findAllTransaccionesBy(Long usuarioId, Map<String, Object> filtros, Long montoMin, Long montoMax, LocalDateTime fechaDesde, LocalDateTime fechaHasta, String sortBy, String orden) {
        Query query = new Query();

        query.addCriteria(Criteria.where("usuarioId").is(usuarioId));

        filtros.forEach((key, value) -> query.addCriteria(Criteria.where(key).is(value)));

        if (montoMin != null || montoMax != null) {
            query.addCriteria(Criteria.where("monto")
                    .gte(montoMin != null ? montoMin : Long.MIN_VALUE)
                    .lte(montoMax != null ? montoMax : Long.MAX_VALUE));
        }
        if (fechaDesde != null || fechaHasta != null) {
            query.addCriteria(Criteria.where("fecha")
                    .gte(fechaDesde != null ? fechaDesde : LocalDateTime.MIN)
                    .lte(fechaHasta != null ? fechaHasta : LocalDateTime.MAX));
        }

        Sort sort = Sort.by(orden.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
                sortBy != null ? sortBy : "fecha");
        query.with(sort);

        return mongoTemplate.find(query, Transaccion.class);
    }
}
