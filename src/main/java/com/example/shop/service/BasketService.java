package com.example.shop.service;

import com.example.shop.model.dao.Product;

import java.util.List;

public interface BasketService {

    List<Product> getProducts();

    void addProduct(Long productId, int quantity);

    void addProductOverride(Long productId, int quantity);

    void deleteProduct(Long productId);

    void clearBasket();
}
