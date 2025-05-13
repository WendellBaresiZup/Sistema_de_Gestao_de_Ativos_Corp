package com.zup.gestaodeativos.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class MovementLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Active active;
    private User user;
    private LocalDateTime movementDate;
    private String action;

    public MovementLog(){
    }

    public MovementLog(Long id, Active active, User user, LocalDateTime movementDate, String action){
        this.id = id;
        this.active = active;
        this.user = user;
        this.movementDate = movementDate;
        this.action = action;
    }

}
