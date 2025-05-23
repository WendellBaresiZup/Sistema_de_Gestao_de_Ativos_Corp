package com.zup.gestaodeativos.services.impl;

import com.zup.gestaodeativos.dto.AssetAssignmentDTO;
import com.zup.gestaodeativos.dto.CreateAssetAssignmentDTO;
import com.zup.gestaodeativos.exceptions.DuplicateResourceException;
import com.zup.gestaodeativos.exceptions.ResourceNotFoundException;
import com.zup.gestaodeativos.models.Active;
import com.zup.gestaodeativos.models.AssetAssignment;
import com.zup.gestaodeativos.models.User;
import com.zup.gestaodeativos.repository.ActiveRepository;
import com.zup.gestaodeativos.repository.AssetAssignmentRepository;
import com.zup.gestaodeativos.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssetAssignmentServiceImplTest {

    @Mock
    private AssetAssignmentRepository assetAssignmentRepository;
    @Mock
    private ActiveRepository activeRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AssetAssignmentServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void associateActive_shouldAssociateAndReturnDTO() {
        CreateAssetAssignmentDTO dto = new CreateAssetAssignmentDTO();
        dto.setActiveId(1L);
        dto.setUserId(2L);

        Active active = new Active();
        active.setId(1L);
        active.setAvailable(true);

        User user = new User();
        user.setId(2L);

        AssetAssignment assignment = AssetAssignment.builder()
                .id(10L)
                .active(active)
                .user(user)
                .assignedAt(LocalDateTime.now())
                .isActive(true)
                .build();

        when(activeRepository.findByIdAndAvailableTrue(1L)).thenReturn(Optional.of(active));
        when(assetAssignmentRepository.findByActiveIdAndIsActiveTrue(1L)).thenReturn(Optional.empty());
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(assetAssignmentRepository.save(any(AssetAssignment.class))).thenReturn(assignment);
        when(activeRepository.save(any(Active.class))).thenReturn(active);

        AssetAssignmentDTO result = service.associateActive(dto);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        verify(activeRepository).save(any(Active.class));
        verify(assetAssignmentRepository).save(any(AssetAssignment.class));
    }

    @Test
    void associateActive_shouldThrowIfActiveNotFound() {
        CreateAssetAssignmentDTO dto = new CreateAssetAssignmentDTO();
        dto.setActiveId(1L);
        when(activeRepository.findByIdAndAvailableTrue(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.associateActive(dto));
    }

    @Test
    void associateActive_shouldThrowIfAlreadyAssociated() {
        CreateAssetAssignmentDTO dto = new CreateAssetAssignmentDTO();
        dto.setActiveId(1L);

        Active active = new Active();
        active.setId(1L);

        when(activeRepository.findByIdAndAvailableTrue(1L)).thenReturn(Optional.of(active));
        when(assetAssignmentRepository.findByActiveIdAndIsActiveTrue(1L)).thenReturn(Optional.of(new AssetAssignment()));

        assertThrows(DuplicateResourceException.class, () -> service.associateActive(dto));
    }

    @Test
    void associateActive_shouldThrowIfUserNotFound() {
        CreateAssetAssignmentDTO dto = new CreateAssetAssignmentDTO();
        dto.setActiveId(1L);
        dto.setUserId(2L);

        Active active = new Active();
        active.setId(1L);

        when(activeRepository.findByIdAndAvailableTrue(1L)).thenReturn(Optional.of(active));
        when(assetAssignmentRepository.findByActiveIdAndIsActiveTrue(1L)).thenReturn(Optional.empty());
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.associateActive(dto));
    }

    @Test
    void removeAssignment_shouldSetUnassignedAtAndSetActiveAvailable() {
        AssetAssignment assignment = AssetAssignment.builder()
                .id(1L)
                .active(new Active())
                .isActive(true)
                .build();

        when(assetAssignmentRepository.findById(1L)).thenReturn(Optional.of(assignment));
        when(assetAssignmentRepository.save(any(AssetAssignment.class))).thenReturn(assignment);
        when(activeRepository.save(any(Active.class))).thenReturn(assignment.getActive());

        service.removeAssignment(1L);

        assertNotNull(assignment.getUnassignedAt());
        assertTrue(assignment.getActive().isAvailable());
        verify(assetAssignmentRepository).save(assignment);
        verify(activeRepository).save(assignment.getActive());
    }

    @Test
    void removeAssignment_shouldThrowIfNotFound() {
        when(assetAssignmentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.removeAssignment(1L));
    }

    @Test
    void findAssignmentById_shouldReturnDTO() {
        AssetAssignment assignment = AssetAssignment.builder().id(1L).build();
        when(assetAssignmentRepository.findById(1L)).thenReturn(Optional.of(assignment));
        Optional<AssetAssignmentDTO> result = service.findAssignmentById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void findAssignmentById_shouldReturnEmpty() {
        when(assetAssignmentRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<AssetAssignmentDTO> result = service.findAssignmentById(1L);
        assertFalse(result.isPresent());
    }

    @Test
    void findAllActiveAssignments_shouldReturnList() {
        List<AssetAssignment> assignments = List.of(AssetAssignment.builder().id(1L).build());
        when(assetAssignmentRepository.findByIsActiveTrue()).thenReturn(assignments);
        List<AssetAssignmentDTO> result = service.findAllActiveAssignments();
        assertEquals(1, result.size());
    }

    @Test
    void findAssignmentsByUser_shouldReturnList() {
        List<AssetAssignment> assignments = List.of(AssetAssignment.builder().id(1L).build());
        when(assetAssignmentRepository.findByUserId(2L)).thenReturn(assignments);
        List<AssetAssignmentDTO> result = service.findAssignmentsByUser(2L);
        assertEquals(1, result.size());
    }
}