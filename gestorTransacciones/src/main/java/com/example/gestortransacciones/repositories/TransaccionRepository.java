package com.example.gestortransacciones.repositories;

import org.example.commons.dto.Transaccion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaccionRepository extends MongoRepository<Transaccion, String>, TransaccionCustomRepository {
    Transaccion getByTransaccionId(Long id);
    //Object save(Transaccion transaccion);
}
