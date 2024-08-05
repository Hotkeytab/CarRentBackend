package com.example.carRent.Auth.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.carRent.Auth.Entity.User;
import com.example.carRent.Auth.Repository.UserRepository;
import com.example.carRent.Shared.Dto.JwtService;
import com.example.carRent.Shared.Dto.LoginRequest;
import com.example.carRent.Shared.Dto.RegisterRequest;
import com.example.carRent.Shared.Dto.UserDto;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;  // Assuming you have a JwtService for generating tokens

    // Register a new user
    public UserDto registerUser(RegisterRequest registerRequest) throws Exception {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new Exception("Username is already taken: " + registerRequest.getUsername());
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new Exception("Email is already taken: " + registerRequest.getEmail());
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());

        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);  // Convert entity to DTO before returning
    }

    // Authenticate user and generate JWT token
    public String authenticateUser(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + loginRequest.getUsername()));

        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return jwtService.generateToken(user);  // Generate JWT token
        } else {
            throw new BadCredentialsException("Invalid credentials");
        }
    }
    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        return userDto;
    }


}

