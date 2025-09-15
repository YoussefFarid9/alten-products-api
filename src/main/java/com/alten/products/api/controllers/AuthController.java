package com.alten.products.api.controllers;

import com.alten.products.api.dtos.AuthCreateAccount;
import com.alten.products.api.dtos.AuthLoginRequest;
import com.alten.products.api.dtos.AuthTokenResponse;
import com.alten.products.api.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/account")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid AuthCreateAccount request) {
        authService.createAccount(request);
    }

    @PostMapping("/token")
    public AuthTokenResponse token(@RequestBody @Valid AuthLoginRequest request) {
        return authService.issueToken(request);
    }
}
