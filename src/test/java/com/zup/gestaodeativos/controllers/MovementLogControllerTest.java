package com.zup.gestaodeativos.controllers;

import com.zup.gestaodeativos.models.MovementLog;
import com.zup.gestaodeativos.services.MovementLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovementLogControllerTest {

    @Mock
    private MovementLogService service;

    @InjectMocks
    private MovementLogController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_shouldReturnOk() {
        MovementLog log = new MovementLog();
        when(service.registerMovement(log)).thenReturn(log);

        ResponseEntity<MovementLog> result = controller.register(log);

        assertEquals(log, result.getBody());
        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    void listAll_shouldReturnList() {
        List<MovementLog> logs = List.of(new MovementLog());
        when(service.listAll()).thenReturn(logs);

        List<MovementLog> result = controller.listAll();

        assertEquals(logs, result);
    }
}