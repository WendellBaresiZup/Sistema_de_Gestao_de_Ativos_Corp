package com.zup.gestaodeativos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ActiveRequestDTO {

    @Schema(description = "Tipo do ativo", example = "Notebook")
    @NotBlank(message = "O tipo do ativo não pode estar em branco")
    private String type;

    @Schema(description = "Modelo do ativo", example = "Dell Inspiron 15")
    private String model;

    @Schema(description = "Número de série do ativo", example = "ABC1234567")
    @NotBlank(message = "O número de série não pode estar em branco")
    private String serialNumber;

    @Schema(description = "Data de aquisição do ativo", example = "2024-01-15")
    @NotNull(message = "A data de aquisição não pode ser nula")
    private LocalDate acquisitionDate;

    @Schema(description = "Data de expiração da garantia", example = "2026-01-15")
    @NotNull(message = "A data de expiração da garantia não pode ser nula")
    private LocalDate warrantyExpires;
}