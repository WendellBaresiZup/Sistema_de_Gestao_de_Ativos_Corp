package com.zup.gestaodeativos.services;

import com.zup.gestaodeativos.dto.AssetAssignmentDTO;
import com.zup.gestaodeativos.dto.CreateAssetAssignmentDTO;

import java.util.List;
import java.util.Optional;

public interface AssetAssignmentService {
    AssetAssignmentDTO associateActive(CreateAssetAssignmentDTO createDto);
    void removeAssignment(Long assignmentId);
    Optional<AssetAssignmentDTO> findAssignmentById(Long assignmentId);
    List<AssetAssignmentDTO> findAllActiveAssignments();
    List<AssetAssignmentDTO> findAssignmentsByUser(Long userId);
}
