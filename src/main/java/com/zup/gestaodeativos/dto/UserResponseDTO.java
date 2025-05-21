package com.zup.gestaodeativos.dto;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private UserRole role;
}