package com.zup.gestaodeativos.controllers;

import com.zup.gestaodeativos.dto.ActiveRequestDTO;
import com.zup.gestaodeativos.dto.ActiveResponseDTO;
import com.zup.gestaodeativos.services.ActiveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/actives")
@Tag(name = "Active", description = "Gerenciamento de Ativos")
@RequiredArgsConstructor
public class ActiveController {

    private final ActiveService activeService;

    @Operation(
            summary = "Criar novo ativo",
            description = "Cria um novo ativo no sistema",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ActiveRequestDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de Ativo",
                                            summary = "Ativo com todos os campos preenchidos",
                                            value = """
                                {
                                  "type": "Notebook",
                                  "model": "Dell Inspiron 15",
                                  "serialNumber": "ABC1234567",
                                  "acquisitionDate": "2024-01-15",
                                  "warrantyExpires": "2026-01-15"
                                }
                                """
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Ativo criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @PostMapping
    public ResponseEntity<ActiveResponseDTO> createActive(@Valid @RequestBody ActiveRequestDTO activeRequestDTO) {
        ActiveResponseDTO newActive = activeService.createActive(activeRequestDTO);
        return new ResponseEntity<>(newActive, HttpStatus.CREATED);
    }

    @Operation(summary = "Buscar ativo por ID", description = "Retorna os dados de um ativo específico")
    @GetMapping("/{id}")
    public ResponseEntity<ActiveResponseDTO> getActiveById(@PathVariable Long id) {
        return activeService.findActiveById(id)
                .map(active -> new ResponseEntity<>(active, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Listar todos os ativos")
    @GetMapping
    public ResponseEntity<List<ActiveResponseDTO>> getAllActives() {
        List<ActiveResponseDTO> allActives = activeService.findAllActives();
        return allActives.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(allActives, HttpStatus.OK);
    }

    @Operation(summary = "Listar ativos disponíveis")
    @GetMapping("/available")
    public ResponseEntity<List<ActiveResponseDTO>> getAvailableActives() {
        List<ActiveResponseDTO> availableActives = activeService.findAvailableActives();
        return availableActives.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(availableActives, HttpStatus.OK);
    }

    @Operation(summary = "Deletar ativo", description = "Remove um ativo do sistema")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActive(@PathVariable Long id) {
        activeService.deleteActive(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}