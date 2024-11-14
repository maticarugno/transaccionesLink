package com.example.validaciontransacciones.service;

import org.example.commons.dto.Transaccion;

public interface ValidadorTransaccion {
    void validar(Transaccion transaccion);
}
