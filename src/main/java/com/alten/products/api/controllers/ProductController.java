package com.alten.products.api.controllers;

import com.alten.products.api.dtos.ProductDto;
import com.alten.products.api.dtos.UpsertProduct;
import com.alten.products.api.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public Page<ProductDto> list(Pageable pageable) {
        return productService.list(pageable);
    }

    @GetMapping("/{id}")
    public ProductDto get(@PathVariable Long id) {
        return productService.get(id);
    }

    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @PostMapping
    public ProductDto create(@RequestBody @Valid UpsertProduct upsertProduct) {
        return productService.create(upsertProduct);
    }

    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @PutMapping("/{id}")
    public ProductDto update(@PathVariable Long id, @RequestBody @Valid UpsertProduct upsertProduct) {
        return productService.update(id, upsertProduct);
    }

    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
