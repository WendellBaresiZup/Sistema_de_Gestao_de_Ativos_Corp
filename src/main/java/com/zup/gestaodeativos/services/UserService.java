package com.zup.gestaodeativos.services;

import com.zup.gestaodeativos.dto.*;
import com.zup.gestaodeativos.dto.UserRole;
import com.zup.gestaodeativos.exceptions.UserExceptions;
import com.zup.gestaodeativos.models.User;
import com.zup.gestaodeativos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponseDTO register(UserRequestDTO dto) {
        String email = dto.getEmail().toLowerCase();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserExceptions("400", "Email já cadastrado", "O email informado já está em uso.");
        }
        // Impede cadastro de admin via API
        if (dto.getNome().equalsIgnoreCase("Administrador") || email.equals("admin@zup.com")) {
            throw new UserExceptions("403", "Ação proibida", "Não é permitido cadastrar admin por este endpoint.");
        }
        User user = User.builder()
                .nome(dto.getNome())
                .email(email)
                .telefone(dto.getTelefone().replaceAll("\\D", ""))
                .senha(dto.getSenha())
                .role(UserRole.USER)
                .build();
        user = userRepository.save(user);
        return toDTO(user);
    }

    public UserResponseDTO login(LoginRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail().toLowerCase())
                .orElseThrow(() -> new UserExceptions("404", "Usuário não encontrado", "Email não cadastrado."));
        if (!user.getSenha().equals(dto.getSenha())) {
            throw new UserExceptions("400", "Senha inválida", "A senha informada está incorreta.");
        }
        return toDTO(user);
    }

    public UserResponseDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserExceptions("404", "Usuário não encontrado", "ID não cadastrado."));
        return toDTO(user);
    }

    public UserResponseDTO findByNome(String nome) {
        User user = userRepository.findByNomeIgnoreCase(nome)
                .orElseThrow(() -> new UserExceptions("404", "Usuário não encontrado", "Nome não cadastrado."));
        return toDTO(user);
    }

    private UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.getTelefone(),
                user.getRole()
        );
    }
}