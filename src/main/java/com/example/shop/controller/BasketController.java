package com.example.shop.controller;

import com.example.shop.mapper.ProductMapper;
import com.example.shop.model.dto.BasketDto;
import com.example.shop.model.dto.ProductDto;
import com.example.shop.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/baskets")
@PreAuthorize("isAuthenticated()")
public class BasketController {

    private final BasketService basketService;
    private final ProductMapper productMapper;

    @GetMapping
    public List<ProductDto> getProducts() {
        return productMapper.daoListToDtoList(basketService.getProducts());
    }

    @PostMapping
    public void addProduct(@RequestBody @Valid BasketDto basketDto) {
        basketService.addProduct(basketDto.getProductId(), basketDto.getQuantity());
    }

    @PostMapping("/override")
    public void addProductOverride(@RequestBody @Valid BasketDto basketDto) {
        basketService.addProductOverride(basketDto.getProductId(), basketDto.getQuantity());
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
