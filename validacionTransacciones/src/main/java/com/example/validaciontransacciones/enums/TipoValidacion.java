package com.example.validaciontransacciones.enums;

import com.example.validaciontransacciones.validaciones.Validacion;
import com.example.validaciontransacciones.validaciones.ValidacionTarjeta;
import com.example.validaciontransacciones.validaciones.ValidacionTransferencia;

public enum TipoValidacion {
    TARJETA(new ValidacionTarjeta()),
    TRANSFERENCIA(new ValidacionTransferencia());

    private final Validacion validacion;

    TipoValidacion(Validacion validacion) {
        this.validacion = validacion;
    }

    public Validacion getValidacion() {
        return validacion;
    }

    public static TipoValidacion fromString(String tipo) {
        return TipoValidacion.valueOf(tipo.toUpperCase());
    }
}
