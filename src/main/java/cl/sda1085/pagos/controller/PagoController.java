package cl.sda1085.pagos.controller;


import cl.sda1085.pagos.dto.PagoRequestDTO;
import cl.sda1085.pagos.dto.PagoResponseDTO;
import cl.sda1085.pagos.repository.PagoRepository;
import cl.sda1085.pagos.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoController {

    //Conexión con 'service'
    private final PagoService pagoService;


    //------------------------------
    //CRUD estándar
    //------------------------------

    //Obtener todos los pagos
    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(pagoService.obtenerTodos());
    }

    //Obtener pago por ID
    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.obtenerPorId(id));
    }

    //Guardar (crear) nuevo pago
    @PostMapping
    public ResponseEntity<PagoResponseDTO> guardar(@Valid @RequestBody PagoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.guardar(dto));
    }

    //Actualizar pago existente
    @PutMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody PagoRequestDTO dto) {
        return ResponseEntity.ok(pagoService.actualizar(id, dto));
    }

    //Eliminar pago
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


    //------------------------------
    //CRUD personalizado
    //------------------------------

    //Buscar historial de pagos de un usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PagoResponseDTO>> obtenerPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(pagoService.obtenerPorUsuario(idUsuario));
    }

    //Buscar pagos asociados a una subasta específica
    @GetMapping("/subasta/{idSubasta}")
    public ResponseEntity<List<PagoResponseDTO>> obtenerPorSubasta(@PathVariable Long idSubasta) {
        return ResponseEntity.ok(pagoService.obtenerPorSubasta(idSubasta));
    }

    //Filtrar pagos por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PagoResponseDTO>> obtenerPorEstado(@PathVariable String estado){
        return ResponseEntity.ok(pagoService.obtenerPorEstado(estado));
    }

    //Cambiar estado del pago
    @PatchMapping("/{id}/estado")
    public ResponseEntity<PagoResponseDTO> actualizarEstado(
            @PathVariable Long id,
            @RequestParam String nuevoEstado) {

        return ResponseEntity.ok(pagoService.actualizarEstado(id, nuevoEstado));
    }

    //Verificar existencia del pago
    @GetMapping("/subasta/{idSubasta}/existe")
    public ResponseEntity<Boolean> existePagoParaSubasta(@PathVariable Long idSubasta) {
        return ResponseEntity.ok(pagoService.existePagoParaSubasta(idSubasta));
    }

    //Buscar pago específico de usuario en subasta
    @GetMapping("/buscar/especifico")
    public ResponseEntity<PagoResponseDTO> buscarPagoEspecifico(@RequestParam Long idSubasta,
                                                                @RequestParam Long idUsuario) {

        return pagoService.buscarPagoEspecifico(idSubasta, idUsuario)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    //Filtrar por método de pago
    @GetMapping("/metodo/{metodo}")
    public ResponseEntity<List<PagoResponseDTO>> buscarPorMetodo(@PathVariable String metodo) {
        return ResponseEntity.ok(pagoService.buscarPorMetodo(metodo));
    }

    //Buscar pagos mayores a un monto
    @GetMapping("/buscar/monto-mayor")
    public ResponseEntity<List<PagoResponseDTO>> buscarPagosMayoresA(@RequestParam BigDecimal monto) {
        return ResponseEntity.ok(pagoService.buscarPagosMayoresA(monto));
    }
}
