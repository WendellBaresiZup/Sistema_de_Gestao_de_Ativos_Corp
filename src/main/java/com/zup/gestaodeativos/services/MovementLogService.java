package com.zup.gestaodeativos.services;

import com.zup.gestaodeativos.models.MovementLog;

import java.util.List;

public interface MovementLogService {
    MovementLog registerMovement(MovementLog log);
    List<MovementLog> listAll();
}
