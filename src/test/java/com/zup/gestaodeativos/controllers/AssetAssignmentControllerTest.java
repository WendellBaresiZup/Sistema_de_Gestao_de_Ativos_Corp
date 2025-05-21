package com.zup.gestaodeativos.controllers;

import com.zup.gestaodeativos.dto.AssetAssignmentDTO;
import com.zup.gestaodeativos.dto.CreateAssetAssignmentDTO;
import com.zup.gestaodeativos.services.AssetAssignmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssetAssignmentControllerTest {

    @Mock
    private AssetAssignmentService assignmentService;

    @InjectMocks
    private AssetAssignmentController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void associateActive_shouldReturnCreated() {
        CreateAssetAssignmentDTO req = new CreateAssetAssignmentDTO();
        AssetAssignmentDTO resp = new AssetAssignmentDTO();
        when(assignmentService.associateActive(req)).thenReturn(resp);

        ResponseEntity<AssetAssignmentDTO> result = controller.associateActive(req);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(resp, result.getBody());
    }

    @Test
    void removeAssignment_shouldReturnNoContent() {
        doNothing().when(assignmentService).removeAssignment(1L);

        ResponseEntity<Void> result = controller.removeAssignment(1L);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(assignmentService).removeAssignment(1L);
    }

    @Test
    void getAssignmentById_found() {
        AssetAssignmentDTO resp = new AssetAssignmentDTO();
        when(assignmentService.findAssignmentById(1L)).thenReturn(Optional.of(resp));

        ResponseEntity<AssetAssignmentDTO> result = controller.getAssignmentById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(resp, result.getBody());
    }

    @Test
    void getAssignmentById_notFound() {
        when(assignmentService.findAssignmentById(1L)).thenReturn(Optional.empty());

        ResponseEntity<AssetAssignmentDTO> result = controller.getAssignmentById(1L);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void getAllActiveAssignments_withContent() {
        List<AssetAssignmentDTO> list = List.of(new AssetAssignmentDTO());
        when(assignmentService.findAllActiveAssignments()).thenReturn(list);

        ResponseEntity<List<AssetAssignmentDTO>> result = controller.getAllActiveAssignments();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(list, result.getBody());
    }

    @Test
    void getAllActiveAssignments_noContent() {
        when(assignmentService.findAllActiveAssignments()).thenReturn(Collections.emptyList());

        ResponseEntity<List<AssetAssignmentDTO>> result = controller.getAllActiveAssignments();

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    void getAssignmentsByUser_withContent() {
        List<AssetAssignmentDTO> list = List.of(new AssetAssignmentDTO());
        when(assignmentService.findAssignmentsByUser(1L)).thenReturn(list);

        ResponseEntity<List<AssetAssignmentDTO>> result = controller.getAssignmentsByUser(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(list, result.getBody());
    }

    @Test
    void getAssignmentsByUser_noContent() {
        when(assignmentService.findAssignmentsByUser(1L)).thenReturn(Collections.emptyList());

        ResponseEntity<List<AssetAssignmentDTO>> result = controller.getAssignmentsByUser(1L);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }
}