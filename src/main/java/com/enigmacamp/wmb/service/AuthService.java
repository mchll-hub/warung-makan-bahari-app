package com.enigmacamp.wmb.service;

import com.enigmacamp.wmb.dto.request.AuthRequest;
import com.enigmacamp.wmb.dto.response.LoginResponse;
import com.enigmacamp.wmb.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse registerCustomer(AuthRequest request);
    RegisterResponse registerAdmin(AuthRequest request);
    LoginResponse login(AuthRequest request);

}
