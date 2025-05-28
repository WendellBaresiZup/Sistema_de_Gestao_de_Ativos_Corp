package com.zup.gestaodeativos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDTO {
        @NotBlank(message = "O nome não pode estar em branco")
        @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres")
        private String nome;

        @NotBlank(message = "O email não pode estar em branco")
        @Email(message = "Formato de email inválido")
        @Size(max = 100, message = "O email deve ter no máximo 100 caracteres")
        private String email;

        @NotBlank(message = "O telefone não pode estar em branco")
        @Size(max = 15, message = "O telefone deve ter no máximo 15 caracteres")
        private String telefone;

        @NotBlank(message = "A senha não pode estar em branco")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        private String senha; 
        @NotNull(message = "O papel do usuário não pode ser nulo")
        private UserRole role;
}