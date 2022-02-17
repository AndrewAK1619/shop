package com.example.shop.controller;

import com.example.shop.model.dto.BasketDto;
import com.example.shop.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/baskets")
@PreAuthorize("isAuthenticated()")
public class BasketController {

    private final BasketService basketService;

    @PostMapping
    public void addProduct(@RequestBody @Valid BasketDto basketDto) {
        basketService.addProduct(basketDto.getProductId(), basketDto.getQuantity());
    }

    @DeleteMapping("/{productId}")
    public void deleteProductFromBasket(@PathVariable Long productId) {
        basketService.deleteProduct(productId);
    }

    @DeleteMapping
    public void clearBasket() {
        basketService.clearBasket();
    }
}
