package com.zup.gestaodeativos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para cadastro de usuário")
public class UserRequestDTO {

        @NotBlank
        @Size(max = 50)
        @Schema(example = "Maria da Silva")
        private String nome;

        @NotBlank
        @Email(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$", flags = Pattern.Flag.CASE_INSENSITIVE)
        @Schema(example = "maria@email.com")
        private String email;

        @NotBlank
        @Pattern(regexp = "\\d{11,}", message = "Telefone deve conter no mínimo 11 dígitos numéricos")
        @Schema(example = "11999999999")
        private String telefone;

        @NotBlank
        @Size(min = 6, max = 20)
        @Schema(example = "senha123")
        private String senha;
}