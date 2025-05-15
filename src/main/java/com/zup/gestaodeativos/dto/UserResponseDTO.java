package com.zup.gestaodeativos.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de resposta de usuário")
public class UserResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;

    @Schema(implementation = UserRole.class)
    private UserRole role;
}