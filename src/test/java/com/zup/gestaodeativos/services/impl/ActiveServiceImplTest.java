package com.zup.gestaodeativos.services.impl;

import com.zup.gestaodeativos.dto.ActiveRequestDTO;
import com.zup.gestaodeativos.dto.ActiveResponseDTO;
import com.zup.gestaodeativos.exceptions.BusinessRuleViolationException;
import com.zup.gestaodeativos.exceptions.DuplicateResourceException;
import com.zup.gestaodeativos.exceptions.ResourceNotFoundException;
import com.zup.gestaodeativos.models.Active;
import com.zup.gestaodeativos.models.AssetAssignment;
import com.zup.gestaodeativos.models.User;
import com.zup.gestaodeativos.repository.ActiveRepository;
import com.zup.gestaodeativos.repository.AssetAssignmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActiveServiceImplTest {

    @Mock
    private ActiveRepository activeRepository;

    @Mock
    private AssetAssignmentRepository assetAssignmentRepository;

    @InjectMocks
    private ActiveServiceImpl activeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createActive_shouldCreateAndReturnActiveResponseDTO() {
        ActiveRequestDTO request = new ActiveRequestDTO();
        request.setSerialNumber("123");
        Active active = new Active();
        active.setSerialNumber("123");
        Active savedActive = new Active();
        savedActive.setId(1L);
        savedActive.setSerialNumber("123");

        when(activeRepository.findBySerialNumber("123")).thenReturn(Optional.empty());
        when(activeRepository.save(any(Active.class))).thenReturn(savedActive);

        ActiveResponseDTO response = activeService.createActive(request);

        assertNotNull(response);
        assertEquals("123", response.getSerialNumber());
        assertFalse(response.isAssociated());
        verify(activeRepository).save(any(Active.class));
    }

    @Test
    void createActive_shouldThrowDuplicateResourceException() {
        ActiveRequestDTO request = new ActiveRequestDTO();
        request.setSerialNumber("123");
        when(activeRepository.findBySerialNumber("123")).thenReturn(Optional.of(new Active()));

        assertThrows(DuplicateResourceException.class, () -> activeService.createActive(request));
    }

    @Test
    void findAvailableActives_shouldReturnOnlyUnassociatedActives() {
        Active active1 = new Active();
        active1.setId(1L);
        Active active2 = new Active();
        active2.setId(2L);

        when(activeRepository.findAll()).thenReturn(List.of(active1, active2));
        when(assetAssignmentRepository.findByActiveIdAndIsActiveTrue(1L)).thenReturn(Optional.empty());
        when(assetAssignmentRepository.findByActiveIdAndIsActiveTrue(2L)).thenReturn(Optional.of(new AssetAssignment()));

        List<ActiveResponseDTO> result = activeService.findAvailableActives();

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void findAllActives_shouldReturnAllActives() {
        Active active1 = new Active();
        active1.setId(1L);
        Active active2 = new Active();
        active2.setId(2L);

        when(activeRepository.findAll()).thenReturn(List.of(active1, active2));
        when(assetAssignmentRepository.findByActiveIdAndIsActiveTrue(anyLong())).thenReturn(Optional.empty());

        List<ActiveResponseDTO> result = activeService.findAllActives();

        assertEquals(2, result.size());
    }

    @Test
    void findActiveById_shouldReturnActiveResponseDTO() {
        Active active = new Active();
        active.setId(1L);

        when(activeRepository.findById(1L)).thenReturn(Optional.of(active));
        when(assetAssignmentRepository.findByActiveIdAndIsActiveTrue(1L)).thenReturn(Optional.empty());

        Optional<ActiveResponseDTO> result = activeService.findActiveById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void findActiveById_shouldReturnEmptyIfNotFound() {
        when(activeRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ActiveResponseDTO> result = activeService.findActiveById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void deleteActive_shouldDeleteIfNotAssociated() {
        Active active = new Active();
        active.setId(1L);

        when(activeRepository.findById(1L)).thenReturn(Optional.of(active));
        when(assetAssignmentRepository.findByActiveIdAndIsActiveTrue(1L)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> activeService.deleteActive(1L));
        verify(activeRepository).delete(active);
    }

    @Test
    void deleteActive_shouldThrowIfNotFound() {
        when(activeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> activeService.deleteActive(1L));
    }

    @Test
    void deleteActive_shouldThrowIfAssociated() {
        Active active = new Active();
        active.setId(1L);

        when(activeRepository.findById(1L)).thenReturn(Optional.of(active));
        when(assetAssignmentRepository.findByActiveIdAndIsActiveTrue(1L)).thenReturn(Optional.of(new AssetAssignment()));

        assertThrows(BusinessRuleViolationException.class, () -> activeService.deleteActive(1L));
    }

    @Test
    void convertToActiveResponseDTO_shouldSetAssociatedFields() {
        Active active = new Active();
        active.setId(1L);

        AssetAssignment assignment = new AssetAssignment();
        User user = new User();
        user.setId(10L);
        user.setNome("Test User");
        assignment.setUser(user);

        when(assetAssignmentRepository.findByActiveIdAndIsActiveTrue(1L)).thenReturn(Optional.of(assignment));

        // Usando reflection para testar método privado
        ActiveResponseDTO dto = org.springframework.test.util.ReflectionTestUtils.invokeMethod(
                activeService, "convertToActiveResponseDTO", active);

        assertTrue(dto.isAssociated());
        assertEquals(10L, dto.getAssociatedUserId());
        assertEquals("Test User", dto.getAssociatedUserName());
    }
}