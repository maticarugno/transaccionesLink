package org.example.compensadortransaccion.service;

import lombok.extern.slf4j.Slf4j;
import org.example.commons.dto.Transaccion;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CompensadorService {
    @RabbitListener(queues = "transacciones_a_compensar")
    public void validar(Transaccion transaccion) {
        log.info("Transaccion fallida recibida: {}", transaccion.getTransaccionId());
        //TODO: realizar tareas de compensacion, como cancelar una posible compra
    }
}
