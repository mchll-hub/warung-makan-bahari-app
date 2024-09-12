package com.enigmacamp.wmb.service.impl;

import com.enigmacamp.wmb.constant.ERole;
import com.enigmacamp.wmb.dto.request.AuthRequest;
import com.enigmacamp.wmb.dto.response.LoginResponse;
import com.enigmacamp.wmb.dto.response.RegisterResponse;
import com.enigmacamp.wmb.entity.Customer;
import com.enigmacamp.wmb.entity.Role;
import com.enigmacamp.wmb.entity.UserCredential;
import com.enigmacamp.wmb.repository.UserCredentialRepository;
import com.enigmacamp.wmb.security.JwtUtil;
import com.enigmacamp.wmb.service.AuthService;
import com.enigmacamp.wmb.service.CustomerService;
import com.enigmacamp.wmb.service.RoleService;
import com.enigmacamp.wmb.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserCredentialRepository userCredentialRepository;
    private final CustomerService customerService;
    private final RoleService roleService;
    private final JwtUtil jwtUtil;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse registerCustomer(AuthRequest request) {
        try {
            //role
            Role role = roleService.getOrSave(Role.builder()
                    .name(ERole.ROLE_CUSTOMER)
                    .build());

            //user credential
            UserCredential userCredential = UserCredential.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            //customer
            Customer customer = Customer.builder()
                    .userCredential(userCredential)
                    .build();
            customerService.createNew(customer);

        return RegisterResponse.builder()
                .username(userCredential.getUsername())
                .role(userCredential.getRole().getName().toString())
                .build();

        } catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "user already exist");
        }
    }

    @Override
    public LoginResponse login(AuthRequest request) {
        //tempat logic untuk login
        validationUtil.validate(request);
        String token = jwtUtil.generateToken("id");
        return LoginResponse.builder()
                .token(token)
                .build();
    }
}
