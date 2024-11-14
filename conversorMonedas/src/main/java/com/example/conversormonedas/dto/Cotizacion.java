package com.example.conversormonedas.dto;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cotizacion")
public record Cotizacion(String moneda, Long cotizacion) {
}
