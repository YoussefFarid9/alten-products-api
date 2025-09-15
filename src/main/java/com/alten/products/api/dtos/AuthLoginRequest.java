package com.alten.products.api.dtos;

import jakarta.validation.constraints.*;

public record AuthLoginRequest(@Email @NotBlank String email, @NotBlank String password) {
}
