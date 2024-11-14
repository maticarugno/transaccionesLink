package com.example.conversormonedas.repositories;

import com.example.conversormonedas.dto.Cotizacion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CotizacionRepository extends MongoRepository<Cotizacion, String> {
    Cotizacion getByMoneda(String moneda);
}
