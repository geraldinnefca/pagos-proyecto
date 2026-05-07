package cl.sda1085.pagos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "pagos")

public class Pago {

    @Id  //
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idSubasta;

    @Column(nullable = false)
    private Long idUsuario;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal monto;

    @Column(nullable = false)  //Pendiente, completado y fallido
    private String estado;

    @Column(nullable = false)  //Tarjeta, transferencia y PayPal
    private String metodo;
}
