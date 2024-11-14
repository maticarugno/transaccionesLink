package com.example.validaciontransacciones.validaciones;

import org.example.commons.dto.Transaccion;

import static org.example.commons.enums.Estados.COMPLETED;

public class ValidacionTarjeta implements Validacion {
    @Override
    public void validar(Transaccion transaccion) {
        //TODO: se deberian hacer validaciones reales, como si el monto supera cierto limite pedir la aprobacion del banco
        transaccion.setEstado(COMPLETED);
    }
}
