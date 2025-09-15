package com.alten.products.api.dtos;

import jakarta.validation.constraints.*;

public record AuthCreateAccount(
        @NotBlank String username,
        @NotBlank String firstname,
        @Email @NotBlank String email,
        @NotBlank String password
) {
}
