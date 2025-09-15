package com.alten.products.api.repositories;

import com.alten.products.api.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {
    Optional<Product> findByCode(String code);

    boolean existsByCode(String code);
}
