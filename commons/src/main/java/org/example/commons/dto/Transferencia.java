package org.example.commons.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.commons.enums.Estados;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Transferencia extends Transaccion {

    private String bankCode;

    public Transferencia(Long transaccionId, Long monto, String moneda, LocalDateTime fecha, Estados status,
                         Long montoMonedaBase, Long usuarioId, String bankCode) {
        super(transaccionId, "transferencia", monto, moneda, fecha, status, montoMonedaBase, usuarioId);
        this.bankCode = bankCode;
    }
}
