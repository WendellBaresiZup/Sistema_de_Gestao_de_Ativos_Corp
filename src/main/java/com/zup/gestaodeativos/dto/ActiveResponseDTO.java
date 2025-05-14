package com.zup.gestaodeativos.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ActiveResponseDTO {
    private Long id;
    private String type;
    private String model;
    private String serialNumber;
    private LocalDate acquisitionDate;
    private LocalDate warrantyExpires;
    private Long currentEmployeeId;
    private String currentEmployeeName;
}
