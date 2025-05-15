package com.zup.gestaodeativos.services;

import com.zup.gestaodeativos.dto.MovementLogRequestDTO;
import com.zup.gestaodeativos.models.Active;
import com.zup.gestaodeativos.models.MovementLog;
import com.zup.gestaodeativos.repository.ActiveRepository;
import com.zup.gestaodeativos.repository.MovementLogRepository;
import com.zup.gestaodeativos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovementLogServiceImpl implements MovementLogService{

    @Override
    public MovementLog registerMovement(MovementLog log) {
        return null;
    }

    @Override
    public List<MovementLog> listAll() {
        return List.of();
    }
}
