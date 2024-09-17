package com.enigmacamp.wmb.service.impl;

import com.enigmacamp.wmb.constant.ERole;
import com.enigmacamp.wmb.dto.request.AuthRequest;
import com.enigmacamp.wmb.dto.response.LoginResponse;
import com.enigmacamp.wmb.dto.response.RegisterResponse;
import com.enigmacamp.wmb.entity.*;
import com.enigmacamp.wmb.repository.UserCredentialRepository;
import com.enigmacamp.wmb.security.JwtUtil;
import com.enigmacamp.wmb.service.AdminService;
import com.enigmacamp.wmb.service.AuthService;
import com.enigmacamp.wmb.service.CustomerService;
import com.enigmacamp.wmb.service.RoleService;
import com.enigmacamp.wmb.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final AdminService adminService;
    private final AuthenticationManager authenticationManager;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse registerCustomer(AuthRequest request) {
        try {
            validationUtil.validate(request);
            //role
            Role role = roleService.getOrSave(Role.builder()
                    .name(ERole.ROLE_CUSTOMER)
                    .build());

            //user credential
            UserCredential userCredential = UserCredential.builder()
                    .username(request.getUsername().toLowerCase())
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
    public RegisterResponse registerAdmin(AuthRequest request) {
        try {
            validationUtil.validate(request);
            //role
            Role role = roleService.getOrSave(Role.builder()
                    .name(ERole.ROLE_ADMIN)
                    .build());

            //user credential
            UserCredential userCredential = UserCredential.builder()
                    .username(request.getUsername().toLowerCase())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            //customer
            Admin admin = Admin.builder()
                    .userCredential(userCredential)
                    .build();
            adminService.createAdmin(admin);

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
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername().toLowerCase(),
                request.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        // object AppUser
        AppUser appUser = (AppUser) authenticate.getPrincipal();
        String token = jwtUtil.generateToken(appUser);

        return LoginResponse.builder()
                .token(token)
                .role(appUser.getRole().name())
                .build();
    }


}
