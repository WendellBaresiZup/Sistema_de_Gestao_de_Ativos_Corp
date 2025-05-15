package com.zup.gestaodeativos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para login de usuário")
public class LoginRequestDTO {

        @NotBlank
        @Email
        @Schema(example = "maria@email.com")
        private String email;

        @NotBlank
        @Schema(example = "senha123")
        private String senha;
}