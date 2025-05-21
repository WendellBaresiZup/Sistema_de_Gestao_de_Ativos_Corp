package com.zup.gestaodeativos.controllers;

import com.zup.gestaodeativos.dto.LoginRequestDTO;
import com.zup.gestaodeativos.dto.UserRequestDTO;
import com.zup.gestaodeativos.dto.UserResponseDTO;
import com.zup.gestaodeativos.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_shouldReturnCreated() {
        UserRequestDTO req = new UserRequestDTO();
        UserResponseDTO resp = new UserResponseDTO();
        when(userService.createUser(req)).thenReturn(resp);

        ResponseEntity<UserResponseDTO> result = controller.register(req);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(resp, result.getBody());
    }

    @Test
    void login_shouldReturnOk() {
        LoginRequestDTO login = new LoginRequestDTO();
        UserResponseDTO resp = new UserResponseDTO();
        when(userService.login(login)).thenReturn(resp);

        ResponseEntity<UserResponseDTO> result = controller.login(login);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(resp, result.getBody());
    }

    @Test
    void findById_found() {
        UserResponseDTO resp = new UserResponseDTO();
        when(userService.findUserById(1L)).thenReturn(Optional.of(resp));

        ResponseEntity<UserResponseDTO> result = controller.findById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(resp, result.getBody());
    }

    @Test
    void findById_notFound() {
        when(userService.findUserById(1L)).thenReturn(Optional.empty());

        ResponseEntity<UserResponseDTO> result = controller.findById(1L);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void getUsersByNome_withContent() {
        List<UserResponseDTO> list = List.of(new UserResponseDTO());
        when(userService.findUsersByName("nome")).thenReturn(list);

        ResponseEntity<List<UserResponseDTO>> result = controller.getUsersByNome("nome");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(list, result.getBody());
    }

    @Test
    void getUsersByNome_noContent() {
        when(userService.findUsersByName("nome")).thenReturn(Collections.emptyList());

        ResponseEntity<List<UserResponseDTO>> result = controller.getUsersByNome("nome");

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }
}