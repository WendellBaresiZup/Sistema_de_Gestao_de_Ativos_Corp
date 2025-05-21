package com.zup.gestaodeativos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateAssetAssignmentDTO {
    @NotNull(message = "O ID do usuário não pode ser nulo")
    private Long userId;

    @NotNull(message = "O ID do ativo não pode ser nulo")
    private Long activeId;
}