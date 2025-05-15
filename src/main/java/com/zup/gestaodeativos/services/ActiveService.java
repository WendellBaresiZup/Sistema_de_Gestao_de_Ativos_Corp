package com.zup.gestaodeativos.services;

import com.zup.gestaodeativos.dto.ActiveRequestDTO;
import com.zup.gestaodeativos.dto.ActiveResponseDTO;

import java.util.List;

public interface ActiveService {
    ActiveResponseDTO create(ActiveRequestDTO activeRequestDTO);
    void delete(Long id);
    List<ActiveResponseDTO> listAll();
}