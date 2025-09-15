package com.alten.products.api.bootstrap;


import lombok.RequiredArgsConstructor;
import com.alten.products.api.models.InventoryStatus;
import com.alten.products.api.models.Product;
import com.alten.products.api.models.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.alten.products.api.repositories.ProductRepository;
import com.alten.products.api.repositories.UserRepository;

import java.math.BigDecimal;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final ProductRepository products;

    @Override
    public void run(String... args) {
        users.findByEmail("admin@admin.com").orElseGet(() -> {
            var user = new User();
            user.setUsername("admin");
            user.setFirstname("Admin");
            user.setEmail("admin@admin.com");
            user.setPasswordHash(encoder.encode("admin"));
            return users.save(user);
        });
        users.findByEmail("user@user.com").orElseGet(() -> {
            var user = new User();
            user.setUsername("user");
            user.setFirstname("User");
            user.setEmail("user@user.com");
            user.setPasswordHash(encoder.encode("user"));
            return users.save(user);
        });

        if (products.count() == 0) {
            products.saveAll(List.of(
                    createProduct("SKU-1001", "Laptop Pro 14", "Thin and powerful", "laptop.png", "Computers", new BigDecimal("1299.00"), 25, "INT-REF-1", 10L, InventoryStatus.INSTOCK, 5),
                    createProduct("SKU-1002", "Wireless Mouse", "Ergonomic", "mouse.png", "Accessories", new BigDecimal("29.90"), 120, "INT-REF-2", 11L, InventoryStatus.INSTOCK, 4),
                    createProduct("SKU-1003", "4K Monitor", "27-inch IPS", "monitor.png", "Monitors", new BigDecimal("349.00"), 8, "INT-REF-3", 12L, InventoryStatus.LOWSTOCK, 4),
                    createProduct("SKU-1004", "Mechanical Keyboard", "RGB", "keyboard.png", "Accessories", new BigDecimal("89.00"), 0, "INT-REF-4", 13L, InventoryStatus.OUTOFSTOCK, 5)
            ));
        }
    }

    private Product createProduct(String code, String name, String desc, String img, String cat,
                                  BigDecimal price, int qty, String iref, Long shellId, InventoryStatus st, int rating) {
        var product = new Product();
        product.setCode(code);
        product.setName(name);
        product.setDescription(desc);
        product.setImage(img);
        product.setCategory(cat);
        product.setPrice(price);
        product.setQuantity(qty);
        product.setInternalReference(iref);
        product.setShellId(shellId);
        product.setInventoryStatus(st);
        product.setRating(rating);
        return product;
    }
}
