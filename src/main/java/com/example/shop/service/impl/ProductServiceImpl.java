package com.example.shop.service.impl;

import com.example.shop.model.dao.Product;
import com.example.shop.repository.ProductRepository;
import com.example.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product update(Long id, Product product) {
        var productDb = getById(id);
        productDb.setId(product.getId());
        productDb.setName(product.getName());
        productDb.setSerialNumber(product.getSerialNumber());
        productDb.setDescription(product.getDescription());
        return productDb;
    }

    @Override
    public Product getById(Long id) {
        log.info("Product none in cache {}", id);
        return productRepository.getById(id);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> getPage(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
