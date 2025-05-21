package com.zup.gestaodeativos.services.impl;

import com.zup.gestaodeativos.models.MovementLog;
import com.zup.gestaodeativos.services.MovementLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovementLogServiceImpl implements MovementLogService {

    @Override
    public MovementLog registerMovement(MovementLog log) {
        return null;
    }

    @Override
    public List<MovementLog> listAll() {
        return List.of();
    }
}
