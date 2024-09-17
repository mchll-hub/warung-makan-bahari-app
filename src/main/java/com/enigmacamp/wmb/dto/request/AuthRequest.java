package com.enigmacamp.wmb.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {
    @NotBlank(message = "username is required")
    @Pattern(regexp = "[]]", message = "Invalid username")
    private String username;
    @NotBlank(message = "password is required")
    private String password;
}
