package com.alten.products.api.services;

import com.alten.products.api.dtos.AddWishlistRequest;
import com.alten.products.api.dtos.WishlistLine;
import com.alten.products.api.models.User;
import com.alten.products.api.models.WishlistItem;
import com.alten.products.api.repositories.ProductRepository;
import com.alten.products.api.repositories.UserRepository;
import com.alten.products.api.repositories.WishlistItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistItemRepository wishlistItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private User current(Authentication auth) {
        var email = ((Jwt) auth.getPrincipal()).getClaimAsString("email");
        return userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }

    public List<WishlistLine> list(Authentication auth) {
        var user = current(auth);
        return wishlistItemRepository.findByUser(user).stream()
                .map(wishlistItem -> new WishlistLine(wishlistItem.getProduct().getId(),
                        wishlistItem.getProduct().getCode(),
                        wishlistItem.getProduct().getName()))
                .toList();
    }

    public void add(Authentication auth, AddWishlistRequest addWishlistRequest) {
        var user = current(auth);
        var product = productRepository.findById(addWishlistRequest.productId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!wishlistItemRepository.existsByUserIdAndProductId(user.getId(), product.getId())) {
            var wishlistItem = new WishlistItem();
            wishlistItem.setUser(user);
            wishlistItem.setProduct(product);
            wishlistItemRepository.save(wishlistItem);
        }
    }

    @Transactional
    public void remove(Authentication auth, Long productId) {
        var user = current(auth);
        wishlistItemRepository.deleteByUserAndProductId(user, productId);
    }
}
