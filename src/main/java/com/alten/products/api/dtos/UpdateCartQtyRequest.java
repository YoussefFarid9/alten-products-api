package com.alten.products.api.dtos;

import jakarta.validation.constraints.Min;

public record UpdateCartQtyRequest(@Min(1) Integer quantity) {
}
