package com.example.gestortransacciones.service.queues;

import org.example.commons.dto.Transaccion;

public interface TransaccionConsumer {
    void recibirTransaccionValidada(Transaccion transaccion);
}
