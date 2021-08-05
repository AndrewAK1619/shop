package com.example.shop.service;

import com.example.shop.model.dao.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Product create(Product product);

    Product update(Long id, Product product);

    Product getById(Long id);

    void deleteById(Long id);

    Page<Product> getPage(Pageable pageable);
}
