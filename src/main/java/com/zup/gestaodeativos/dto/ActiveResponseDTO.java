package com.zup.gestaodeativos.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ActiveResponseDTO {
    private Long id;
    private String type;
    private String model;
    private String serialNumber;
    private LocalDate acquisitionDate;
    private LocalDate warrantyExpires;
    private boolean associated;
    private Long associatedUserId;
    private String associatedUserName;
}
