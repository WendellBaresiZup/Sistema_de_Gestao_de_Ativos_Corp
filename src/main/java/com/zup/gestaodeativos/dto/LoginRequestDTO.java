package com.zup.gestaodeativos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
        @NotBlank(message = "O email não pode estar em branco")
        @Email(message = "Formato de email inválido")
        private String email;

        @NotBlank(message = "A senha não pode estar em branco")
        private String senha;
}