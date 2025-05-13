package com.zup.gestaodeativos.dto;

import lombok.Data;

@Data
public class MovementLogRequestDTO {
    private Long activeId;
    private Long userId;
    private String action;

    public MovementLogRequestDTO(Long activeId, Long userId, String action){
        this.activeId = activeId;
        this.userId = userId;
        this.action = action;
    }
}
