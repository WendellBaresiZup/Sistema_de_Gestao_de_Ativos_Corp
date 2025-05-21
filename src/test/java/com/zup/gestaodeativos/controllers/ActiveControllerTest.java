package com.zup.gestaodeativos.controllers;

import com.zup.gestaodeativos.dto.ActiveRequestDTO;
import com.zup.gestaodeativos.dto.ActiveResponseDTO;
import com.zup.gestaodeativos.services.ActiveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActiveControllerTest {

    @Mock
    private ActiveService activeService;

    @InjectMocks
    private ActiveController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createActive_shouldReturnCreated() {
        ActiveRequestDTO req = new ActiveRequestDTO();
        ActiveResponseDTO resp = new ActiveResponseDTO();
        when(activeService.createActive(req)).thenReturn(resp);

        ResponseEntity<ActiveResponseDTO> result = controller.createActive(req);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(resp, result.getBody());
    }

    @Test
    void getActiveById_found() {
        ActiveResponseDTO resp = new ActiveResponseDTO();
        when(activeService.findActiveById(1L)).thenReturn(Optional.of(resp));

        ResponseEntity<ActiveResponseDTO> result = controller.getActiveById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(resp, result.getBody());
    }

    @Test
    void getActiveById_notFound() {
        when(activeService.findActiveById(1L)).thenReturn(Optional.empty());

        ResponseEntity<ActiveResponseDTO> result = controller.getActiveById(1L);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void getAllActives_withContent() {
        List<ActiveResponseDTO> list = List.of(new ActiveResponseDTO());
        when(activeService.findAllActives()).thenReturn(list);

        ResponseEntity<List<ActiveResponseDTO>> result = controller.getAllActives();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(list, result.getBody());
    }

    @Test
    void getAllActives_noContent() {
        when(activeService.findAllActives()).thenReturn(Collections.emptyList());

        ResponseEntity<List<ActiveResponseDTO>> result = controller.getAllActives();

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    void getAvailableActives_withContent() {
        List<ActiveResponseDTO> list = List.of(new ActiveResponseDTO());
        when(activeService.findAvailableActives()).thenReturn(list);

        ResponseEntity<List<ActiveResponseDTO>> result = controller.getAvailableActives();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(list, result.getBody());
    }

    @Test
    void getAvailableActives_noContent() {
        when(activeService.findAvailableActives()).thenReturn(Collections.emptyList());

        ResponseEntity<List<ActiveResponseDTO>> result = controller.getAvailableActives();

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    void deleteActive_shouldReturnNoContent() {
        doNothing().when(activeService).deleteActive(1L);

        ResponseEntity<Void> result = controller.deleteActive(1L);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(activeService).deleteActive(1L);
    }
}