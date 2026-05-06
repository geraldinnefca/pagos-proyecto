package cl.sda1085.pagos.repository;

import cl.sda1085.pagos.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    //Buscar todos los pagos de una subasta específica
    List<Pago> findByIdSubasta(Long idSubasta);

    //Buscar todos los pagos realizados por un usuario
    List<Pago> findByIdUsuario(Long idUsuario);

    //Filtrar pagos por estado
    List<Pago> findByEstado(String estado);

    //Verificar si una subasta ya tiene un pago registrado
    boolean existsByIdSubasta(Long idSubasta);

    //Buscar el pago específico de un usuario para una subasta
    Optional<Pago> findByIdSubastaAndIdUsuario(Long idSubasta, Long idUsuario);

    //Buscar pagos realizados con un método específico
    List<Pago> findByMetodo(String metodo);

    //Buscar pagos wue superen un monto
    List<Pago> findByMontoGreaterThan(BigDecimal monto);
}
