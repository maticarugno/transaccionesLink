package org.example.commons.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.commons.enums.Estados;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "tipo",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Tarjeta.class, name = "tarjeta"),
        @JsonSubTypes.Type(value = Transferencia.class, name = "transferencia"),
        @JsonSubTypes.Type(value = P2P.class, name = "p2p")
})
@Document(collection = "transaccion")
public abstract class Transaccion {
    @Id
    private Long transaccionId;
    private String tipo;
    private Long monto;
    private String moneda;
    private LocalDateTime fecha;
    private Estados estado;
    private Long montoMonedaBase;
    private Long usuarioId;

}
