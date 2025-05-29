package com.zup.gestaodeativos.services.impl;

import com.zup.gestaodeativos.dto.LoginRequestDTO;
import com.zup.gestaodeativos.dto.UserRequestDTO;
import com.zup.gestaodeativos.dto.UserResponseDTO;
import com.zup.gestaodeativos.exceptions.BusinessRuleViolationException;
import com.zup.gestaodeativos.exceptions.DuplicateResourceException;
import com.zup.gestaodeativos.models.User;
import com.zup.gestaodeativos.repository.UserRepository;
import com.zup.gestaodeativos.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        if (userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Usuário com este email já existe.");
        }

        User user = new User();
        BeanUtils.copyProperties(userRequestDTO, user);

        User savedUser = userRepository.save(user);

        UserResponseDTO responseDTO = new UserResponseDTO();
        BeanUtils.copyProperties(savedUser, responseDTO);
        return responseDTO;
    }

    @Override
    public UserResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new BusinessRuleViolationException("Email ou senha inválidos."));

        if (!user.getSenha().equals(loginRequestDTO.getSenha())) {
            throw new BusinessRuleViolationException("Email ou senha inválidos.");
        }

        UserResponseDTO responseDTO = new UserResponseDTO();
        BeanUtils.copyProperties(user, responseDTO);
        return responseDTO;
    }

    @Override
    public Optional<UserResponseDTO> findUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    UserResponseDTO responseDTO = new UserResponseDTO();
                    BeanUtils.copyProperties(user, responseDTO);
                    return responseDTO;
                });
    }

    @Override
    public List<UserResponseDTO> findUsersByName(String name) {
        List<User> users = userRepository.findByNomeContainingIgnoreCase(name);
        return users.stream()
                .map(user -> {
                    UserResponseDTO responseDTO = new UserResponseDTO();
                    BeanUtils.copyProperties(user, responseDTO);
                    return responseDTO;
                })
                .collect(Collectors.toList());
    }
    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleViolationException("Usuário não encontrado."));
        userRepository.delete(user);
    }

    @Override
    public List<UserResponseDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> {
                    UserResponseDTO responseDTO = new UserResponseDTO();
                    BeanUtils.copyProperties(user, responseDTO);
                    return responseDTO;
                })
                .collect(Collectors.toList());
    }
}