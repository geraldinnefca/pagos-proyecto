package cl.sda1085.pagos.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoRequestDTO {

    //ID se genera automaticamente

    @NotNull(message = "El ID de la subasta es obligatorio.")
    private Long idSubasta;

    @NotNull(message = "El ID del usuario es obligatorio.")
    private Long idUsuario;

    @NotNull(message = "El monto es obligatorio.")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero.")
    private BigDecimal monto;

    @NotBlank(message = "El método de pago no debe estar vacío.")
    private String metodo;
}
