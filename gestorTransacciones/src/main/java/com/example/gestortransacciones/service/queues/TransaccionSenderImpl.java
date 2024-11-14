package com.example.gestortransacciones.service.queues;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.commons.dto.Transaccion;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static org.example.commons.enums.Estados.FAILED;

@Service
@AllArgsConstructor
@Slf4j
public class TransaccionSenderImpl implements TransaccionSender {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void enviarTransaccionParaValidar(Transaccion transaccion) {
        rabbitTemplate.convertAndSend("transacciones_a_validar", transaccion);
    }

    @Override
    public void enviarTransaccionParaCompensacion(Transaccion transaccion) {
        rabbitTemplate.convertAndSend("transacciones_a_compensar", transaccion);
    }


}
