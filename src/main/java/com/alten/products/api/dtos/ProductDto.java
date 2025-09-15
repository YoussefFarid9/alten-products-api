package com.alten.products.api.dtos;

import com.alten.products.api.models.InventoryStatus;
import com.alten.products.api.models.Product;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductDto(
        Long id, String code, String name, String description, String image, String category,
        BigDecimal price, Integer quantity, String internalReference, Long shellId,
        InventoryStatus inventoryStatus, Integer rating, Instant createdAt, Instant updatedAt
) {
    public static ProductDto from(Product product) {
        return new ProductDto(
                product.getId(), product.getCode(), product.getName(), product.getDescription(), product.getImage(), product.getCategory(),
                product.getPrice(), product.getQuantity(), product.getInternalReference(), product.getShellId(),
                product.getInventoryStatus(), product.getRating(), product.getCreatedAt(), product.getUpdatedAt()
        );
    }
}
