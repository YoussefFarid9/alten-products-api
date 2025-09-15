package com.alten.products.api.dtos;

import jakarta.validation.constraints.Min;

public record AddToCartRequest(Long productId, @Min(1) Integer quantity) {
}
