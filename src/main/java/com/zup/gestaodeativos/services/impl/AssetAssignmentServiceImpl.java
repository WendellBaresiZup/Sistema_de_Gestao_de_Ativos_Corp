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
import com.zup.gestaodeativos.services.AssetAssignmentService;
import com.zup.gestaodeativos.mapper.AssetAssignmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssetAssignmentServiceImpl implements AssetAssignmentService {

    private final AssetAssignmentRepository assetAssignmentRepository;
    private final ActiveRepository activeRepository;
    private final UserRepository userRepository;

    @Autowired
    public AssetAssignmentServiceImpl(AssetAssignmentRepository assetAssignmentRepository,
                                      ActiveRepository activeRepository,
                                      UserRepository userRepository) {
        this.assetAssignmentRepository = assetAssignmentRepository;
        this.activeRepository = activeRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public AssetAssignmentDTO associateActive(CreateAssetAssignmentDTO createDto) {
        Long activeId = createDto.getActiveId();
        Long userId = createDto.getUserId();

        Active active = activeRepository.findByIdAndAvailableTrue(activeId)
                .orElseThrow(() -> new ResourceNotFoundException("Ativo com ID " + activeId + " não encontrado ou não disponível."));

        assetAssignmentRepository.findByActiveIdAndIsActiveTrue(activeId)
                .ifPresent(a -> {
                    throw new DuplicateResourceException("O ativo com ID " + activeId + " já está associado a outro usuário.");
                });

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID " + userId + " não encontrado."));

        AssetAssignment newAssignment = AssetAssignment.builder()
                .active(active)
                .user(user)
                .assignedAt(LocalDateTime.now())
                .isActive(true)
                .build();
        AssetAssignment savedAssignment = assetAssignmentRepository.save(newAssignment);

        active.setAvailable(false);
        activeRepository.save(active);

        return AssetAssignmentMapper.toDto(savedAssignment);
    }

    @Override
    @Transactional
    public void removeAssignment(Long assignmentId) {
        AssetAssignment assignment = assetAssignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Associação não encontrada com ID: " + assignmentId));

        assignment.setUnassignedAt(LocalDateTime.now());
        assetAssignmentRepository.save(assignment);

        Active activeEntity = assignment.getActive();
        if (activeEntity != null) {
            activeEntity.setAvailable(true);
            activeRepository.save(activeEntity);
        }
    }

    @Override
    public Optional<AssetAssignmentDTO> findAssignmentById(Long assignmentId) {
        return assetAssignmentRepository.findById(assignmentId)
                .map(AssetAssignmentMapper::toDto);
    }

    @Override
    public List<AssetAssignmentDTO> findAllActiveAssignments() {
        return assetAssignmentRepository.findByIsActiveTrue().stream()
                .map(AssetAssignmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AssetAssignmentDTO> findAssignmentsByUser(Long userId) {
        return assetAssignmentRepository.findByUserId(userId).stream()
                .map(AssetAssignmentMapper::toDto)
                .collect(Collectors.toList());
    }
}
