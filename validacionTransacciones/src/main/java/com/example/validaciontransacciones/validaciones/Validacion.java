package com.example.validaciontransacciones.validaciones;

import org.example.commons.dto.Transaccion;

public interface Validacion {
    void validar(Transaccion transaccion);
}
