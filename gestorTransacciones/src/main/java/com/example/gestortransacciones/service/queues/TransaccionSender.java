package com.example.gestortransacciones.service.queues;

import org.example.commons.dto.Transaccion;

public interface TransaccionSender {
    void enviarTransaccionParaValidar(Transaccion transaccion);
    void enviarTransaccionParaCompensacion(Transaccion transaccion);
}
