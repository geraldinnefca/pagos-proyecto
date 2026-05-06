package cl.sda1085.pagos.service;

import cl.sda1085.pagos.dto.PagoRequestDTO;
import cl.sda1085.pagos.dto.PagoResponseDTO;
import cl.sda1085.pagos.model.Pago;
import cl.sda1085.pagos.repository.PagoRepository;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagoService {

    //Conexión con 'repository'
    private final PagoRepository pagoRepository;

    //Método de apoyo para encapsulamiento de datos
    private PagoResponseDTO mapToResponseDTO(Pago pago){
        return new PagoResponseDTO(
                pago.getId(),
                pago.getIdSubasta(),
                pago.getIdUsuario(),
                pago.getMonto(),
                pago.getEstado(),
                pago.getMetodo()
        );
    }

    //Método auxiliar de conversión (reutilizable)
    private PagoResponseDTO convertirADTO(Pago pago){
        return new PagoResponseDTO(
                pago.getId(),
                pago.getIdSubasta(),
                pago.getIdUsuario(),
                pago.getMonto(),
                pago.getEstado(),
                pago.getMetodo()
        );
    }

    //Obtener todos los pagos
    public List<PagoResponseDTO> obtenerTodos(){
        return pagoRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    //Obtener pago por ID
    public Optional<PagoResponseDTO> obtenerPorId(Long id){
        return pagoRepository.findById(id)
                .map(this::mapToResponseDTO);
    }

    //Crear (guardar) pago
    public PagoResponseDTO guardar(PagoRequestDTO dto){
        if (pagoRepository.existsByIdSubasta(dto.getIdSubasta())){
            throw new RuntimeException("Esta subasta ya tiene un pago registrado.");
        }

        Pago pago = new Pago();
        pago.setIdSubasta(dto.getIdSubasta());
        pago.setIdUsuario(dto.getIdUsuario());
        pago.setMonto(dto.getMonto());
        pago.setMetodo(dto.getMetodo());
        pago.setEstado("PENDIENTE");  //Todo pago inicia como 'PENDIENTE'

        //Guardar en la base de datos
        Pago pagoGuardado = (pagoRepository.save(pago));

        //Devolver la respuesta como DTO
        return convertirADTO(pagoGuardado);
    }

    //Actualizar pago
    public Optional<PagoResponseDTO> actualizar(Long id, PagoRequestDTO dto){
        return pagoRepository.findById(id).map(pagoExistente -> {
           pagoExistente.setMonto(dto.getMonto());
           pagoExistente.setMetodo(dto.getMetodo());

           //El estado suele actualizarse mediante un método específico de "confirmación"
            return mapToResponseDTO(pagoRepository.save(pagoExistente));
        });
    }

    //Eliminar pago
    public void eliminar(Long id){
        pagoRepository.deleteById(id);
    }


    //CRUD personalizado

    //Buscar pagos asociados a una subasta específica
    public List<PagoResponseDTO> obtenerPorSubasta(Long idSubasta){
        return pagoRepository.findByIdSubasta(idSubasta).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    //Buscar historial de pagos de un usuario
    public List<PagoResponseDTO> obtenerPorUsuario(Long idUsuario){
        return pagoRepository.findByIdUsuario(idUsuario).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    //Filtrar pagos por estado
    public List<PagoResponseDTO> obtenerPorEstado(String estado){
        return pagoRepository.findByEstado(estado).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    //Cambiar estado del pago
    public Optional<PagoResponseDTO> actualizarEstado(Long id, String nuevoEstado){
        return pagoRepository.findById(id).map(pago -> {
            pago.setEstado(nuevoEstado);
            return mapToResponseDTO(pagoRepository.save(pago));
        });
    }

    //Verificar existencia del pago
    public boolean existePagoParaSubasta(Long idSubasta){
        return pagoRepository.existsByIdSubasta(idSubasta);
    }

    //Buscar pago específico
    public Optional<PagoResponseDTO> buscarPagoEspecifico(Long idSubasta, Long idUsuario){
        return pagoRepository.findByIdSubastaAndIdUsuario(idSubasta, idUsuario)
                .map(this::mapToResponseDTO);
    }

    //Filtrar por método de pago
    public List<PagoResponseDTO> buscarPorMetodo(String metodo){
        return pagoRepository.findByMetodo(metodo).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    //Buscar pagos que superen un monto
    public List<PagoResponseDTO> buscarPagosMayoresA(BigDecimal monto){
        return pagoRepository.findByMontoGreaterThan(monto).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
}
