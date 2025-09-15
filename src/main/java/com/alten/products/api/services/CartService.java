package com.alten.products.api.services;

import com.alten.products.api.dtos.AddToCartRequest;
import com.alten.products.api.dtos.CartLine;
import com.alten.products.api.dtos.UpdateCartQtyRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.alten.products.api.models.CartItem;
import com.alten.products.api.models.Product;
import com.alten.products.api.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.alten.products.api.repositories.CartItemRepository;
import com.alten.products.api.repositories.ProductRepository;
import com.alten.products.api.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private User current(Authentication auth) {
        var email = ((Jwt) auth.getPrincipal()).getClaimAsString("email");
        return userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }

    public List<CartLine> list(Authentication auth) {
        var user = current(auth);
        return cartItemRepository.findByUser(user).stream()
                .map(cartItem -> new CartLine(cartItem.getProduct().getId(), cartItem.getProduct().getCode(),
                        cartItem.getProduct().getName(), cartItem.getQuantity()))
                .toList();
    }

    public void add(Authentication auth, AddToCartRequest req) {
        var user = current(auth);
        Product product = productRepository.findById(req.productId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        var existingInCartOpt = cartItemRepository.findByUserAndProduct(user, product);
        if (existingInCartOpt.isPresent()) {
            var ExistingInCart = existingInCartOpt.get();
            ExistingInCart.setQuantity(ExistingInCart.getQuantity() + Math.max(1, req.quantity()));
            cartItemRepository.save(ExistingInCart);
        } else {
            var cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(Math.max(1, req.quantity()));
            cartItemRepository.save(cartItem);
        }
    }

    public void update(Authentication auth, Long productId, UpdateCartQtyRequest req) {
        var user = current(auth);
        var product = productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var cartItem = cartItemRepository.findByUserAndProduct(user, product).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        cartItem.setQuantity(Math.max(1, req.quantity()));
        cartItemRepository.save(cartItem);
    }

    @Transactional
    public void remove(Authentication auth, Long productId) {
        var user = current(auth);
        var product = this.productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        cartItemRepository.deleteByUserAndProduct(user, product);
    }
}
