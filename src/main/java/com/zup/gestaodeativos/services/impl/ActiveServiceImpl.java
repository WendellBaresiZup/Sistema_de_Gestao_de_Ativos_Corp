package com.zup.gestaodeativos.services.impl;

import com.zup.gestaodeativos.dto.ActiveRequestDTO;
import com.zup.gestaodeativos.dto.ActiveResponseDTO;
import com.zup.gestaodeativos.exceptions.BusinessRuleViolationException;
import com.zup.gestaodeativos.exceptions.DuplicateResourceException;
import com.zup.gestaodeativos.exceptions.ResourceNotFoundException;
import com.zup.gestaodeativos.models.Active;
import com.zup.gestaodeativos.models.AssetAssignment;
import com.zup.gestaodeativos.repository.ActiveRepository;
import com.zup.gestaodeativos.repository.AssetAssignmentRepository;
import com.zup.gestaodeativos.services.ActiveService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActiveServiceImpl implements ActiveService {

    private final ActiveRepository activeRepository;
    private final AssetAssignmentRepository assetAssignmentRepository;

    @Autowired
    public ActiveServiceImpl(ActiveRepository activeRepository, AssetAssignmentRepository assetAssignmentRepository) {
        this.activeRepository = activeRepository;
        this.assetAssignmentRepository = assetAssignmentRepository;
    }

    @Override
    @Transactional
    public ActiveResponseDTO createActive(ActiveRequestDTO activeRequestDTO) {
        if (activeRepository.findBySerialNumber(activeRequestDTO.getSerialNumber()).isPresent()) {
            throw new DuplicateResourceException("Ativo com este número de série já existe.");
        }

        Active active = new Active();
        BeanUtils.copyProperties(activeRequestDTO, active);
        Active savedActive = activeRepository.save(active);

        ActiveResponseDTO responseDTO = new ActiveResponseDTO();
        BeanUtils.copyProperties(savedActive, responseDTO);
        responseDTO.setAssociated(false);
        return responseDTO;
    }

    @Override
    public List<ActiveResponseDTO> findAvailableActives() {
        return activeRepository.findAll().stream()
                .filter(active -> assetAssignmentRepository.findByActiveIdAndIsActiveTrue(active.getId()).isEmpty())
                .map(this::convertToActiveResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ActiveResponseDTO> findAllActives() {
        return activeRepository.findAll().stream()
                .map(this::convertToActiveResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ActiveResponseDTO> findActiveById(Long id) {
        return activeRepository.findById(id)
                .map(this::convertToActiveResponseDTO);
    }

    @Override
    @Transactional
    public void deleteActive(Long id) {
        Active active = activeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ativo não encontrado com ID: " + id));

        Optional<AssetAssignment> currentAssignmentOpt = assetAssignmentRepository.findByActiveIdAndIsActiveTrue(active.getId());
        if (currentAssignmentOpt.isPresent()) {
            throw new BusinessRuleViolationException("Não é possível deletar um ativo que está atualmente associado. Remova a associação primeiro.");
        }
        activeRepository.delete(active);
    }

    private ActiveResponseDTO convertToActiveResponseDTO(Active active) {
        ActiveResponseDTO dto = new ActiveResponseDTO();
        BeanUtils.copyProperties(active, dto);

        Optional<AssetAssignment> currentAssignmentOpt = assetAssignmentRepository.findByActiveIdAndIsActiveTrue(active.getId());
        if (currentAssignmentOpt.isPresent()) {
            AssetAssignment currentAssignment = currentAssignmentOpt.get();
            dto.setAssociated(true);
            dto.setAssociatedUserId(currentAssignment.getUser().getId());
            dto.setAssociatedUserName(currentAssignment.getUser().getNome());
        } else {
            dto.setAssociated(false);
        }
        return dto;
    }
}