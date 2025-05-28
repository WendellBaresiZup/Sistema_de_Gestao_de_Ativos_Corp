package com.zup.gestaodeativos.services;


import com.zup.gestaodeativos.dto.LoginRequestDTO;
import com.zup.gestaodeativos.dto.UserRequestDTO;
import com.zup.gestaodeativos.dto.UserResponseDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);
    UserResponseDTO login(LoginRequestDTO loginRequestDTO);
    Optional<UserResponseDTO> findUserById(Long id);
    List<UserResponseDTO> findUsersByName(String name);
}