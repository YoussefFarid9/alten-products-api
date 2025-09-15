package com.alten.products.api.repositories;


import com.alten.products.api.models.User;
import com.alten.products.api.models.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findByUser(User user);

    void deleteByUserAndProductId(User user, Long productId);

    boolean existsByUserIdAndProductId(Long userId, Long productId);
}
