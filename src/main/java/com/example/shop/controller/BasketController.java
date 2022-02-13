package com.example.shop.controller;

import com.example.shop.model.dto.BasketDto;
import com.example.shop.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/baskets")
public class BasketController {

    private final BasketService basketService;

    @PostMapping
    public void addProduct(@RequestBody @Valid BasketDto basketDto) {
        basketService.addProduct(basketDto.getProductId(), basketDto.getQuantity());
    }
}
