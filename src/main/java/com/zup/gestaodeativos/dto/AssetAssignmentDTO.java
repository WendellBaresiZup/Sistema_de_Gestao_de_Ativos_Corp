package com.zup.gestaodeativos.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AssetAssignmentDTO {
    private Long id;
    private Long activeId;
    private String activeSerialNumber;
    private String activeType;
    private Long userId;
    private String userName;
    private String userEmail;
    private LocalDateTime assignedAt;
    private LocalDateTime unassignedAt;
    private boolean isActive;
}