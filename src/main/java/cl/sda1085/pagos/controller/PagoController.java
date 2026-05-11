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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pagos")


public class PagoController {

    private final PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(pagoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<PagoResponseDTO> guardar(@Valid @RequestBody PagoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody PagoRequestDTO dto) {
        return ResponseEntity.ok(pagoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PagoResponseDTO>> obtenerPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(pagoService.obtenerPorUsuario(idUsuario));
    }

    @GetMapping("/subasta/{idSubasta}")
    public ResponseEntity<List<PagoResponseDTO>> obtenerPorSubasta(@PathVariable Long idSubasta) {
        return ResponseEntity.ok(pagoService.obtenerPorSubasta(idSubasta));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<PagoResponseDTO> actualizarEstado(
            @PathVariable Long id,
            @RequestParam String nuevoEstado) {

        return ResponseEntity.ok(pagoService.actualizarEstado(id, nuevoEstado));
    }

}
