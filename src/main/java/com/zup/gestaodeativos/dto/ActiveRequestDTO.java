package com.zup.gestaodeativos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ActiveRequestDTO {
    private String type;
    private String model;
    private String serialNumber;
    private LocalDate acquisitionDate;
    private LocalDate warrantyExpires;
}