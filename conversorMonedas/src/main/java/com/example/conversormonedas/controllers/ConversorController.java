package com.example.conversormonedas.controllers;

import com.example.conversormonedas.service.ConversorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ConversorController implements ConversorApi {

    private ConversorService conversorService;

    @Override
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test conversor OK");
    }

    @Override
    public ResponseEntity<Long> convertir(Long monto, String moneda) {
        return ResponseEntity.ok(conversorService.convertir(monto, moneda));
    }
}
