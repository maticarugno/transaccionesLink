package com.example.conversormonedas.service;

import org.springframework.stereotype.Service;

@Service
public interface ConversorService {
    Long convertir(Long monto, String moneda);
}
