package com.zup.gestaodeativos.dto;

import com.zup.gestaodeativos.models.Active;
import com.zup.gestaodeativos.models.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovementLogResponseDTO {
    private Long id;
    private Active active;
    private User user;
    private LocalDateTime movementDate;
    private String action;

    public MovementLogResponseDTO (Long id, Active active, User user, LocalDateTime movementDate, String action){
        this.id = id;
        this.active = active;
        this.user = user;
        this.movementDate = movementDate;
        this.action = action;
    }

    public MovementLogResponseDTO(){

    }
}
