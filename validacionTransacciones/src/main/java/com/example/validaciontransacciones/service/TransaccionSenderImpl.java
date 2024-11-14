package com.example.validaciontransacciones.service;

import lombok.AllArgsConstructor;
import org.example.commons.dto.Transaccion;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransaccionSenderImpl implements TransaccionSender {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void enviarTransaccionValidada(Transaccion transaccion) {
        rabbitTemplate.convertAndSend("transacciones_validadas", transaccion);
    }
}
