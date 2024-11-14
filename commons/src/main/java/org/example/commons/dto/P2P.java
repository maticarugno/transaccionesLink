package org.example.commons.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.commons.enums.Estados;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class P2P extends Transaccion {
    private Long emisorId;
    private Long receptorId;
    private String nota;

    public P2P(Long transaccionId, Long monto, String moneda, LocalDateTime fecha, Estados status,
                         Long montoMonedaBase, Long usuarioId, Long emisorId, Long receptorId, String nota) {
        super(transaccionId, "p2p", monto, moneda, fecha, status, montoMonedaBase, usuarioId);
        this.emisorId = emisorId;
        this.receptorId = receptorId;
        this.nota = nota;
    }
}
