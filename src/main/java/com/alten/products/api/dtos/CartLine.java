package com.alten.products.api.dtos;

public record CartLine(Long productId, String code, String name, Integer quantity) {
}
