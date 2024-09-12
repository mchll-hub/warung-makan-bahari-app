package com.enigmacamp.wmb.service.impl;

import com.enigmacamp.wmb.entity.AppUser;
import com.enigmacamp.wmb.entity.UserCredential;
import com.enigmacamp.wmb.repository.UserCredentialRepository;
import com.enigmacamp.wmb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserCredentialRepository userCredentialRepository;

    //verifikasi JWT
    @Override
    public AppUser loadUserByUserId(String id) {
        UserCredential userCredential = userCredentialRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Invalid credential"));
        return AppUser.builder()
                .id(userCredential.getId())
                .username(userCredential.getUsername())
                .password(userCredential.getPassword())
                .build();
    }

    //verifikasi authentikasi login
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserCredential userCredential = userCredentialRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Invalid credential"));
        return AppUser.builder()
                .id(userCredential.getId())
                .username(userCredential.getUsername())
                .password(userCredential.getPassword())
                .build();
    }
}
