package com.example.carRent.Auth.Service;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.carRent.Auth.Entity.User;
import com.example.carRent.Auth.Repository.UserRepository;
import com.example.carRent.Shared.Dto.JwtService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository; 
    @MockBean
    private JwtService jwtService; 

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    public CustomUserDetailsServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername("testUser");
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("testPassword", userDetails.getPassword());
    }

    @Test
    public void testLoadUserByUsernameNotFound() {
        when(userRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("unknownUser");
        });
    }
}