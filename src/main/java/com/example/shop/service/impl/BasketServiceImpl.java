package com.example.shop.service.impl;

import com.example.shop.exception.ExceededQuantityException;
import com.example.shop.model.dao.Basket;
import com.example.shop.model.dao.Product;
import com.example.shop.model.dao.User;
import com.example.shop.repository.BasketRepository;
import com.example.shop.service.BasketService;
import com.example.shop.service.ProductService;
import com.example.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final ProductService productService;
    private final UserService userService;

    @Override
    public List<Product> getProducts() {
        return null;
    }

    @Override
    @Transactional
    public void addProduct(Long productId, int quantity) {
        User currentUser = userService.getCurrentUser();
        basketRepository.findByProductIdAndUserId(productId, currentUser.getId())
                .ifPresentOrElse(basket -> {
                    if (basket.getProduct().getQuantity() < quantity + basket.getQuantity()) {
                        throw new ExceededQuantityException("Not enough product quantity");
                    }
                    basket.setQuantity(basket.getQuantity() + quantity);
                }, () -> {
                    Product productById = productService.getById(productId);
                    if (productById.getQuantity() < quantity) {
                        throw new ExceededQuantityException("Not enough product quantity");
                    }
                    basketRepository.save(Basket.builder()
                            .user(currentUser)
                            .product(productById)
                            .quantity(quantity)
                            .build());
                });
    }

    @Override
    public void addProductOverride(Long productId, int quantity) {

    }

    @Override
    public void deleteProduct(Long productId) {

    }

    @Override
    public void clearBasket() {

    }
}
