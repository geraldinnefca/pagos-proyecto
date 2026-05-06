package cl.sda1085.pagos.dto;

//No hay anotaciones de validación.
//DTO de salida.

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PagoResponseDTO {

    private Long id;
    private Long idSubasta;
    private Long idUsuario;
    private BigDecimal monto;
    private String estado;
    private String metodo;
}
