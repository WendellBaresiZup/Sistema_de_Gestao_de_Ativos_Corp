package com.zup.gestaodeativos.controllers;

import com.zup.gestaodeativos.models.MovementLog;
import com.zup.gestaodeativos.services.MovementLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovementLogController {
    @Autowired
    private MovementLogService service;

    @PostMapping
    public ResponseEntity<MovementLog> register(@RequestBody MovementLog log){
        return ResponseEntity.ok(service.registerMovement(log));
    }

    public List<MovementLog> listAll(){
        return service.listAll();
    }
}
