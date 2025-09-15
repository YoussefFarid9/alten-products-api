package com.alten.products.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "users", indexes = @Index(name = "ux_user_email", columnList = "email", unique = true))
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String firstname;
    @Email
    @NotBlank
    private String email;
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    @CreationTimestamp
    private Instant createdAt;
}
