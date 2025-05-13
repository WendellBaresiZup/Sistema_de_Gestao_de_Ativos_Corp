package com.zup.gestaodeativos.services;

import com.zup.gestaodeativos.models.MovementLog;
import com.zup.gestaodeativos.repository.MovementLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovementLogServiceImpl implements MovementLogService{
    @Autowired
    private MovementLogRepository repository;

    public MovementLog registerMovement(MovementLog log){
        log.setMovementDate(LocalDateTime.now());
        return repository.save(log);
    }

    public List<MovementLog> listAll(){
        return repository.findAll();
    }
}
