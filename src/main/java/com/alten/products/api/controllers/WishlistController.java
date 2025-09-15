package com.alten.products.api.controllers;


import com.alten.products.api.dtos.AddWishlistRequest;
import com.alten.products.api.dtos.WishlistLine;
import com.alten.products.api.services.WishlistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    @GetMapping
    public List<WishlistLine> list(Authentication auth) {
        return wishlistService.list(auth);
    }

    @PostMapping
    public void add(Authentication auth, @RequestBody @Valid AddWishlistRequest addWishlistRequest) {
        wishlistService.add(auth, addWishlistRequest);
    }

    @DeleteMapping("/{productId}")
    public void remove(Authentication auth, @PathVariable Long productId) {
        wishlistService.remove(auth, productId);
    }
}
