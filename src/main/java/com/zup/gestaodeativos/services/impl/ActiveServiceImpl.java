package com.zup.gestaodeativos.services.impl;

import com.zup.gestaodeativos.dto.ActiveRequestDTO;
import com.zup.gestaodeativos.dto.ActiveResponseDTO;
import com.zup.gestaodeativos.models.Active;
import com.zup.gestaodeativos.repository.ActiveRepository;
import com.zup.gestaodeativos.services.ActiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActiveServiceImpl implements ActiveService {

    @Autowired
    private ActiveRepository activeRepository;

    @Override
    public ActiveResponseDTO create(ActiveRequestDTO activeRequestDTO) {
        Active active = new Active();
        active.setType(activeRequestDTO.getType());
        active.setModel(activeRequestDTO.getModel());
        active.setSerialNumber(activeRequestDTO.getSerialNumber());
        active.setAcquisitionDate(activeRequestDTO.getAcquisitionDate());
        active.setWarrantyExpires(activeRequestDTO.getWarrantyExpires());

        Active saved = activeRepository.save(active);

        ActiveResponseDTO response = new ActiveResponseDTO();
        response.setId(saved.getId());
        response.setType(saved.getType());
        response.setModel(saved.getModel());
        response.setSerialNumber(saved.getSerialNumber());
        response.setAcquisitionDate(saved.getAcquisitionDate());
        response.setWarrantyExpires(saved.getWarrantyExpires());

        return response;
    }
    @Override
    public void delete(Long id) {
        if (!activeRepository.existsById(id)) {
            throw new RuntimeException("Ativo não encontrado para exclusão");
        }
        activeRepository.deleteById(id);
    }
    @Override
    public List<ActiveResponseDTO> listAll() {
        return activeRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private ActiveResponseDTO toResponseDTO(Active active) {
        ActiveResponseDTO response = new ActiveResponseDTO();
        response.setId(active.getId());
        response.setType(active.getType());
        response.setModel(active.getModel());
        response.setSerialNumber(active.getSerialNumber());
        response.setAcquisitionDate(active.getAcquisitionDate());
        response.setWarrantyExpires(active.getWarrantyExpires());
        return response;
    }
}
