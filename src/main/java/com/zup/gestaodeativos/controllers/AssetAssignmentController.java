package com.zup.gestaodeativos.controllers;

import com.zup.gestaodeativos.dto.AssetAssignmentDTO;
import com.zup.gestaodeativos.dto.CreateAssetAssignmentDTO;
import com.zup.gestaodeativos.services.AssetAssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@Tag(name = "Asset Assignment", description = "Gerenciamento de Atribuição de Ativos")
@RequiredArgsConstructor
public class AssetAssignmentController {

    private final AssetAssignmentService assignmentService;

    @Operation(
            summary = "Associar ativo a usuário",
            description = "Associa um ativo a um usuário",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CreateAssetAssignmentDTO.class),
                            examples = @ExampleObject(value = """
                                           {
                                             "userId": 1,
                                             "activeId": 2
                                           }
                                           """)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Ativo associado com sucesso",
                            content = @Content(schema = @Schema(implementation = AssetAssignmentDTO.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @PostMapping("/associate")
    public ResponseEntity<AssetAssignmentDTO> associateActive(@Valid @org.springframework.web.bind.annotation.RequestBody CreateAssetAssignmentDTO createDto) {
        AssetAssignmentDTO assignment = assignmentService.associateActive(createDto);
        return new ResponseEntity<>(assignment, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Remover associação de ativo",
            description = "Remove uma associação de ativo existente",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Associação removida com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Associação não encontrada"),
                    @ApiResponse(responseCode = "400", description = "Estado inválido para remoção"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAssignment(@PathVariable Long id) {
        assignmentService.removeAssignment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Buscar associação por ID", description = "Retorna os dados de uma associação de ativo específica")
    @GetMapping("/{id}")
    public ResponseEntity<AssetAssignmentDTO> getAssignmentById(@PathVariable Long id) {
        return assignmentService.findAssignmentById(id)
                .map(assignment -> new ResponseEntity<>(assignment, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Listar todas as associações ativas")
    @GetMapping("/active")
    public ResponseEntity<List<AssetAssignmentDTO>> getAllActiveAssignments() {
        List<AssetAssignmentDTO> activeAssignments = assignmentService.findAllActiveAssignments();
        return activeAssignments.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(activeAssignments, HttpStatus.OK);
    }

    @Operation(summary = "Listar associações por usuário")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AssetAssignmentDTO>> getAssignmentsByUser(@PathVariable Long userId) {
        List<AssetAssignmentDTO> userAssignments = assignmentService.findAssignmentsByUser(userId);
        return userAssignments.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(userAssignments, HttpStatus.OK);
    }
}