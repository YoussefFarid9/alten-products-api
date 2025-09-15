package com.alten.products.api.controllers;


import com.alten.products.api.dtos.AddToCartRequest;
import com.alten.products.api.dtos.CartLine;
import com.alten.products.api.dtos.UpdateCartQtyRequest;
import com.alten.products.api.services.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public List<CartLine> list(Authentication auth) {
        return cartService.list(auth);
    }

    @PostMapping
    public void add(Authentication auth, @RequestBody @Valid AddToCartRequest addToCartRequest) {
        cartService.add(auth, addToCartRequest);
    }

    @PutMapping("/{productId}")
    public void update(Authentication auth, @PathVariable Long productId,
                       @RequestBody @Valid UpdateCartQtyRequest updateCartQtyRequest) {
        cartService.update(auth, productId, updateCartQtyRequest);
    }

    @DeleteMapping("/{productId}")
    public void remove(Authentication auth, @PathVariable Long productId) {
        cartService.remove(auth, productId);
    }
}
