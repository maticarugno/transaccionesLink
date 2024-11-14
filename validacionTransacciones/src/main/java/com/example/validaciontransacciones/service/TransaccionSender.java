package com.example.validaciontransacciones.service;

import org.example.commons.dto.Transaccion;

public interface TransaccionSender {
    void enviarTransaccionValidada(Transaccion transaccion);
}
