package com.example.conversormonedas.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("conversor")
public interface ConversorApi {
    @GetMapping
    ResponseEntity<String> test();

    @GetMapping("/convertirAMonedaBase")
    ResponseEntity<Long> convertir(@RequestParam Long monto, @RequestParam String moneda);
}