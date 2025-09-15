package com.alten.products.api.services;

import com.alten.products.api.dtos.ProductDto;
import com.alten.products.api.dtos.UpsertProduct;
import com.alten.products.api.models.Product;
import com.alten.products.api.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Page<ProductDto> list(Pageable pageable) {
        return productRepository.findAll(pageable).map(ProductDto::from);
    }

    public ProductDto get(Long productId) {
        return productRepository.findById(productId).map(ProductDto::from).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public ProductDto create(UpsertProduct upsertProduct) {
        if (productRepository.existsByCode(upsertProduct.code()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "code exists");

        var product = new Product();
        createCopy(upsertProduct, product);

        return ProductDto.from(productRepository.save(product));
    }

    public ProductDto update(Long productId, UpsertProduct upsertProduct) {
        var product = productRepository.findById(productId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!product.getCode().equals(upsertProduct.code()) && productRepository.existsByCode(upsertProduct.code()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "code exists");
        createCopy(upsertProduct, product);

        return ProductDto.from(productRepository.save(product));
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        productRepository.deleteById(id);
    }

    private void createCopy(UpsertProduct upsertProduct, Product product) {
        product.setCode(upsertProduct.code());
        product.setName(upsertProduct.name());
        product.setDescription(upsertProduct.description());
        product.setImage(upsertProduct.image());
        product.setCategory(upsertProduct.category());
        product.setPrice(upsertProduct.price());
        product.setQuantity(upsertProduct.quantity());
        product.setInternalReference(upsertProduct.internalReference());
        product.setShellId(upsertProduct.shellId());
        product.setInventoryStatus(upsertProduct.inventoryStatus());
        product.setRating(upsertProduct.rating());
    }
}
