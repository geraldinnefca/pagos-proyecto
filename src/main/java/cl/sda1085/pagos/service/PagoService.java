package cl.sda1085.pagos.service;

import cl.sda1085.pagos.dto.PagoRequestDTO;
import cl.sda1085.pagos.dto.PagoResponseDTO;
import cl.sda1085.pagos.exception.PagoDuplicadoException;
import cl.sda1085.pagos.exception.PagoNoEncontradoException;
import cl.sda1085.pagos.model.Pago;
import cl.sda1085.pagos.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

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
    public PagoResponseDTO obtenerPorId(Long id){
        return pagoRepository.findById(id)
                .map(this::mapToResponseDTO)
                .orElseThrow(() -> new PagoNoEncontradoException(id));

    }

    //Crear (guardar) pago
    public PagoResponseDTO guardar(PagoRequestDTO dto){
        log.info("Iniciando registro de pago: Subasta #{} - Usuario #{} - Monto: ${}",
                dto.getIdSubasta(), dto.getIdUsuario(), dto.getMonto());

        if (pagoRepository.existsByIdSubasta(dto.getIdSubasta())) {
            log.warn("BLOQUEADO: Intento de pago duplicado para la subasta ID: {}", dto.getIdSubasta());
            throw new PagoDuplicadoException(dto.getIdSubasta());
        }

        Pago pago = new Pago();
        pago.setIdSubasta(dto.getIdSubasta());
        pago.setIdUsuario(dto.getIdUsuario());
        pago.setMonto(dto.getMonto());
        pago.setMetodo(dto.getMetodo());
        pago.setEstado("PENDIENTE");  //Todo pago inicia como 'PENDIENTE'

        //Guardar en la base de datos
        Pago pagoGuardado = pagoRepository.save(pago);

        //Devolver la respuesta como DTO
        log.info("Pago procesado exitosamente. ID Transacción: {}", pagoGuardado.getId());
        return convertirADTO(pagoGuardado);
    }

    //Actualizar pago
    public PagoResponseDTO actualizar(Long id, PagoRequestDTO dto){
        return pagoRepository.findById(id).map(pagoExistente -> {
            pagoExistente.setMonto(dto.getMonto());
            pagoExistente.setMetodo(dto.getMetodo());
            log.info("Actualizando pago ID: {}", id);
            return mapToResponseDTO(pagoRepository.save(pagoExistente));
        }).orElseThrow(() -> new PagoNoEncontradoException(id));
    }

    //Eliminar pago
    public void eliminar(Long id){
        log.warn("Eliminando registro de pago con ID: {}", id);
        pagoRepository.deleteById(id);
        log.info("Confirmación: Pago ID {} eliminado de la base de datos.", id);
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
    public PagoResponseDTO actualizarEstado(Long id, String nuevoEstado) {
        return pagoRepository.findById(id).map(pago -> {
            pago.setEstado(nuevoEstado);
            log.info("Cambiando estado de pago ID: {} a {}", id, nuevoEstado);
            return mapToResponseDTO(pagoRepository.save(pago));
        }).orElseThrow(() -> new PagoNoEncontradoException(id));

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
