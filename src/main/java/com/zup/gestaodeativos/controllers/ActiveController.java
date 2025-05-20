package com.zup.gestaodeativos.controlles;

import com.zup.gestaodeativos.dto.ActiveRequestDTO;
import com.zup.gestaodeativos.dto.ActiveResponseDTO;
import com.zup.gestaodeativos.services.ActiveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actives")
@Tag(name = "Active", description = "Gerenciamento de Ativos")
public class ActiveController {

    @Autowired
    private ActiveService activeService;

    @Operation(
            summary = "Cadastrar um Ativo.",
            description = "Método para cadastrar um ativo.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Ativo cadastrado com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Ativo ja cadastrado.", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.", content = @Content)
            }
    )

    @PostMapping ("/create")
    public ResponseEntity<ActiveResponseDTO> createActive(@RequestBody ActiveRequestDTO activeRequestDTO) {
        ActiveResponseDTO createdActive = activeService.create(activeRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdActive);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActive(@PathVariable Long id) {
        try {
            activeService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping ("All")
    public ResponseEntity<List<ActiveResponseDTO>> getAllActives() {
        List<ActiveResponseDTO> actives = activeService.listAll();
        return ResponseEntity.ok(actives);
    }
    @GetMapping("/available")
    public List<ActiveResponseDTO> getAvailableActives() {
        return activeService.findByAvailableTrue();
    }
}