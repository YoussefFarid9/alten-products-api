package com.alten.products.api.services;


import com.alten.products.api.configs.JwtProps;
import com.alten.products.api.dtos.AuthCreateAccount;
import com.alten.products.api.dtos.AuthLoginRequest;
import com.alten.products.api.dtos.AuthTokenResponse;
import lombok.RequiredArgsConstructor;
import com.alten.products.api.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.alten.products.api.repositories.UserRepository;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final JwtProps jwtProps;

    public void createAccount(AuthCreateAccount req) {
        if (userRepository.existsByEmail(req.email())) throw new ResponseStatusException(HttpStatus.CONFLICT, "Email exists");
        var user = new User();
        user.setUsername(req.username());
        user.setFirstname(req.firstname());
        user.setEmail(req.email());
        user.setPasswordHash(passwordEncoder.encode(req.password()));
        userRepository.save(user);
    }

    public AuthTokenResponse issueToken(AuthLoginRequest req) {
        var user = userRepository.findByEmail(req.email()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad credentials"));

        if (!passwordEncoder.matches(req.password(), user.getPasswordHash()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad credentials");

        var now = Instant.now();
        var exp = now.plusSeconds(jwtProps.expiration());
        String scope = "user" + (user.getEmail().equalsIgnoreCase("admin@admin.com") ? " admin" : "");

        var claims = JwtClaimsSet.builder()
                .issuer(jwtProps.issuer())
                .issuedAt(now)
                .expiresAt(exp)
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("scope", scope)
                .build();

        JwsHeader jws = JwsHeader.with(MacAlgorithm.HS256).type("JWT").build();
        String token = jwtEncoder.encode(JwtEncoderParameters.from(jws, claims)).getTokenValue();
        return new AuthTokenResponse(token);
    }
}
