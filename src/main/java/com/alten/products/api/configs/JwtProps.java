package com.alten.products.api.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.jwt")
public record JwtProps(String issuer, String secret, long expiration) {
}
