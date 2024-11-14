package org.example.commons.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.commons.enums.Estados;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Tarjeta extends Transaccion {
    private String cardId;
    private String mccCode;

    public Tarjeta(Long transaccionId, Long monto, String moneda, LocalDateTime fecha, Estados status,
                   Long montoMonedaBase, Long usuarioId, String cardId, String mccCode) {
        super(transaccionId, "tarjeta", monto, moneda, fecha, status, montoMonedaBase, usuarioId);
        this.cardId = cardId;
        this.mccCode = mccCode;
    }
}
