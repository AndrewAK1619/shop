package com.example.shop.service;

import com.example.shop.model.dao.Product;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    @CachePut(cacheNames = "product", key = "#result.id")
    Product create(Product product);

    @CachePut(cacheNames = "product", key = "#result.id")
    Product update(Long id, Product product);

    @Cacheable(cacheNames = "product", key = "#id")
    Product getById(Long id);

    @CacheEvict(cacheNames = "product", key = "#id")
    void deleteById(Long id);

    Page<Product> getPage(Pageable pageable);
}
