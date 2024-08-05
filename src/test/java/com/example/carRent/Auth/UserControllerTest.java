package com.example.carRent.Auth;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.carRent.Auth.Service.UserService;
import com.example.carRent.Shared.Dto.JwtService;
import com.example.carRent.Shared.Dto.RegisterRequest;
import com.example.carRent.Shared.Dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService; // Mock JwtService

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRegisterUser_Success() throws Exception {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("testUser");
        registerRequest.setPassword("password");
        registerRequest.setEmail("test@example.com");
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Doe");

        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setEmail("test@example.com");
        userDto.setFirstName("John");
                 userDto.setLastName("Doe");


        when(userService.registerUser(any(RegisterRequest.class))).thenReturn(userDto);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("testUser"));
    }
}