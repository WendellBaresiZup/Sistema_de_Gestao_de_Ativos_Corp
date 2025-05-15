package com.zup.gestaodeativos.controlles;

import com.zup.gestaodeativos.dto.*;
import com.zup.gestaodeativos.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "Gerenciamento de Usuários")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Cadastrar novo usuário",
            description = "Endpoint para cadastro de usuário comum",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UserRequestDTO.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "nome": "Maria da Silva",
                      "email": "maria@email.com",
                      "telefone": "11999999999",
                      "senha": "senha123"
                    }
                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuário cadastrado com sucesso",
                            content = @Content(
                                    schema = @Schema(implementation = UserResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                        {
                          "id": 1,
                          "nome": "Maria da Silva",
                          "email": "maria@email.com",
                          "telefone": "11999999999",
                          "role": "USER"
                        }
                        """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO dto) {
        return ResponseEntity.status(201).body(userService.register(dto));
    }

    @Operation(
            summary = "Login de usuário",
            description = "Endpoint para login de usuário",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = LoginRequestDTO.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "email": "maria@email.com",
                      "senha": "senha123"
                    }
                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login realizado com sucesso",
                            content = @Content(
                                    schema = @Schema(implementation = UserResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                        {
                          "id": 1,
                          "nome": "Maria da Silva",
                          "email": "maria@email.com",
                          "telefone": "11999999999",
                          "role": "USER"
                        }
                        """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Credenciais inválidas")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@Valid @RequestBody LoginRequestDTO login) {
        return ResponseEntity.ok(userService.login(login));
    }

    @Operation(summary = "Buscar usuário por ID", description = "Endpoint para buscar usuário pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Operation(summary = "Buscar usuário por nome", description = "Endpoint para buscar usuário pelo nome")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<UserResponseDTO> findByNome(@PathVariable String nome) {
        return ResponseEntity.ok(userService.findByNome(nome));
    }
}