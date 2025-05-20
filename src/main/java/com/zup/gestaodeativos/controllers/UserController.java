package com.zup.gestaodeativos.controllers;

import com.zup.gestaodeativos.dto.LoginRequestDTO;
import com.zup.gestaodeativos.dto.UserRequestDTO;
import com.zup.gestaodeativos.dto.UserResponseDTO;
import com.zup.gestaodeativos.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "Gerenciamento de Usuários")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Cadastrar novo usuário",
            description = "Endpoint para cadastro de usuário comum",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UserRequestDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de cadastro",
                                            value = """
                                {
                                  "nome": "Maria da Silva",
                                  "email": "maria@email.com",
                                  "telefone": "11999999999",
                                  "senha": "senha123",
                                  "role": "ADMIN"
                                }
                                """
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @org.springframework.web.bind.annotation.RequestBody UserRequestDTO dto) {
        UserResponseDTO createdUser = userService.createUser(dto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Login de usuário",
            description = "Endpoint para login de usuário",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = LoginRequestDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de login",
                                            value = """
                                {
                                  "email": "maria@email.com",
                                  "senha": "senha123"
                                }
                                """
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Credenciais inválidas")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@Valid @org.springframework.web.bind.annotation.RequestBody LoginRequestDTO login) {
        UserResponseDTO loggedInUser = userService.login(login);
        return ResponseEntity.ok(loggedInUser);
    }

    @Operation(summary = "Buscar usuário por ID", description = "Endpoint para buscar usuário pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        return userService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Buscar usuários por nome", description = "Endpoint para buscar usuários pelo nome")
    @GetMapping("/search/name")
    public ResponseEntity<List<UserResponseDTO>> getUsersByNome(@RequestParam String nome) {
        List<UserResponseDTO> users = userService.findUsersByName(nome);
        return users.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(users, HttpStatus.OK);
    }
}