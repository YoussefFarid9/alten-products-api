package com.alten.products.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "products", indexes = {
        @Index(name = "ix_product_code", columnList = "code", unique = true),
        @Index(name = "ix_product_category", columnList = "category")
})
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    @Column(length = 2000)
    private String description;
    private String image;
    @NotBlank
    private String category;
    @NotNull
    @DecimalMin("0.0")
    @Digits(integer = 12, fraction = 2)
    private BigDecimal price;
    @NotNull
    @Min(0)
    private Integer quantity;
    private String internalReference;
    private Long shellId;
    @Enumerated(EnumType.STRING)
    @NotNull
    private InventoryStatus inventoryStatus;
    @NotNull
    @Min(0)
    @Max(5)
    private Integer rating;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;
}
