package com.example.gestortransacciones.service.queues;

import com.example.gestortransacciones.service.GestorTransaccion;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.commons.dto.Transaccion;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class TransaccionConsumerImpl implements TransaccionConsumer{

    private final GestorTransaccion gestorTransaccion;

    @Override
    @RabbitListener(queues = "transacciones_validadas")
    public void recibirTransaccionValidada(Transaccion transaccion) {
        log.info("Transaccion validada recibida: {}", transaccion.getTransaccionId());
        gestorTransaccion.procesarTransaccionValidada(transaccion);
    }
}
