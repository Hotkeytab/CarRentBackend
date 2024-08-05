package com.example.carRent.Auth.Service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.carRent.Auth.Entity.User;
import com.example.carRent.Auth.Repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public void init() {
        JpaRepositoryFactory factory = new JpaRepositoryFactory(entityManager);
        this.userRepository = factory.getRepository(UserRepository.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}
