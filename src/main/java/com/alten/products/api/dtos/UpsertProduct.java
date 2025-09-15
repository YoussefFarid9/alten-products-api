package com.alten.products.api.dtos;

import jakarta.validation.constraints.*;
import com.alten.products.api.models.InventoryStatus;

import java.math.BigDecimal;

public record UpsertProduct(
        @NotBlank String code,
        @NotBlank String name,
        String description,
        String image,
        @NotBlank String category,
        @NotNull @DecimalMin("0.0") @Digits(integer = 12, fraction = 2) BigDecimal price,
        @NotNull @Min(0) Integer quantity,
        String internalReference,
        Long shellId,
        @NotNull InventoryStatus inventoryStatus,
        @NotNull @Min(0) @Max(5) Integer rating
) {
}
