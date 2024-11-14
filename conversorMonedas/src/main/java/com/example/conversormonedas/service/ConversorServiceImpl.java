package com.example.conversormonedas.service;

import com.example.conversormonedas.dto.Cotizacion;
import com.example.conversormonedas.exceptions.CotizacionNoEncontradaException;
import com.example.conversormonedas.repositories.CotizacionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConversorServiceImpl implements ConversorService {

    private final CotizacionRepository cotizacionRepository;

    @Override
    public Long convertir(Long monto, String moneda) {
        return monto / getCotizacion(moneda).cotizacion();
    }

    private Cotizacion getCotizacion(String moneda) {
        Cotizacion cotizacion = cotizacionRepository.getByMoneda(moneda);
        if (cotizacion == null) {
            throw new CotizacionNoEncontradaException("No se encontro ninguna cotizacion para la moneda " + moneda);
        }
        return cotizacion;
    }
}
