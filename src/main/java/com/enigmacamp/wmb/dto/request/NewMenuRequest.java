package com.enigmacamp.wmb.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewMenuRequest {
    @NotBlank(message = "name is required")
    private String name;

    @NotNull
    @Min(value = 0, message = "price must be greater than or equal to zero")
    private Long price;

    private MultipartFile multipartFile;
}
