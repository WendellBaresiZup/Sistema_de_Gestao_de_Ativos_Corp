package com.zup.gestaodeativos.services;

import com.zup.gestaodeativos.dto.ActiveRequestDTO;
import com.zup.gestaodeativos.dto.ActiveResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ActiveService {
    ActiveResponseDTO createActive(ActiveRequestDTO activeRequestDTO);
    List<ActiveResponseDTO> findAvailableActives();
    List<ActiveResponseDTO> findAllActives();
    Optional<ActiveResponseDTO> findActiveById(Long id);
    void deleteActive(Long id);
}