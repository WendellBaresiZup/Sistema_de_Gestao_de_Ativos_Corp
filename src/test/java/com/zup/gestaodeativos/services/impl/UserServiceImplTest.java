package com.zup.gestaodeativos.services.impl;

import com.zup.gestaodeativos.dto.*;
import com.zup.gestaodeativos.exceptions.BusinessRuleViolationException;
import com.zup.gestaodeativos.exceptions.DuplicateResourceException;
import com.zup.gestaodeativos.models.User;
import com.zup.gestaodeativos.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.BeanUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_shouldCreateAndReturnUserResponseDTO() {
        UserRequestDTO request = new UserRequestDTO();
        request.setEmail("test@email.com");
        request.setNome("Test User");
        request.setSenha("123");

        when(userRepository.findByEmail("test@email.com")).thenReturn(Optional.empty());

        User user = new User();
        BeanUtils.copyProperties(request, user);

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail("test@email.com");
        savedUser.setNome("Test User");
        savedUser.setSenha("123");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponseDTO response = userService.createUser(request);

        assertNotNull(response);
        assertEquals("test@email.com", response.getEmail());
        assertEquals("Test User", response.getNome());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_shouldThrowDuplicateResourceException() {
        UserRequestDTO request = new UserRequestDTO();
        request.setEmail("test@email.com");

        when(userRepository.findByEmail("test@email.com")).thenReturn(Optional.of(new User()));

        assertThrows(DuplicateResourceException.class, () -> userService.createUser(request));
    }

    @Test
    void login_shouldReturnUserResponseDTO_whenCredentialsAreCorrect() {
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("test@email.com");
        loginRequest.setSenha("123");

        User user = new User();
        user.setId(1L);
        user.setEmail("test@email.com");
        user.setNome("Test User");
        user.setSenha("123");

        when(userRepository.findByEmail("test@email.com")).thenReturn(Optional.of(user));

        UserResponseDTO response = userService.login(loginRequest);

        assertNotNull(response);
        assertEquals("test@email.com", response.getEmail());
        assertEquals("Test User", response.getNome());
    }

    @Test
    void login_shouldThrowBusinessRuleViolationException_whenUserNotFound() {
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("notfound@email.com");
        loginRequest.setSenha("123");

        when(userRepository.findByEmail("notfound@email.com")).thenReturn(Optional.empty());

        assertThrows(BusinessRuleViolationException.class, () -> userService.login(loginRequest));
    }

    @Test
    void login_shouldThrowBusinessRuleViolationException_whenPasswordIsIncorrect() {
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("test@email.com");
        loginRequest.setSenha("wrong");

        User user = new User();
        user.setEmail("test@email.com");
        user.setSenha("123");

        when(userRepository.findByEmail("test@email.com")).thenReturn(Optional.of(user));

        assertThrows(BusinessRuleViolationException.class, () -> userService.login(loginRequest));
    }

    @Test
    void findUserById_shouldReturnUserResponseDTO() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@email.com");
        user.setNome("Test User");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<UserResponseDTO> result = userService.findUserById(1L);

        assertTrue(result.isPresent());
        assertEquals("test@email.com", result.get().getEmail());
    }

    @Test
    void findUserById_shouldReturnEmptyIfNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<UserResponseDTO> result = userService.findUserById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void findUsersByName_shouldReturnListOfUserResponseDTO() {
        User user1 = new User();
        user1.setId(1L);
        user1.setNome("Test User 1");
        user1.setEmail("user1@email.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setNome("Test User 2");
        user2.setEmail("user2@email.com");

        when(userRepository.findByNomeContainingIgnoreCase("Test")).thenReturn(List.of(user1, user2));

        List<UserResponseDTO> result = userService.findUsersByName("Test");

        assertEquals(2, result.size());
        assertEquals("user1@email.com", result.get(0).getEmail());
        assertEquals("user2@email.com", result.get(1).getEmail());
    }
}