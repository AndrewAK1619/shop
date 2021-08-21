package com.example.shop.controller;

import com.example.shop.model.dao.Product;
import com.example.shop.model.dao.User;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.history.Revision;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/history")
public class HistoryController {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @GetMapping("/users/{id}")
    public Page<Revision<Integer, User>> getHistoryUser(@PathVariable Long id, @RequestParam int page,
                                                        @RequestParam int size) {
        return userRepository.findRevisions(id, PageRequest.of(page, size));
    }

    @GetMapping("products/{id}")
    public Page<Revision<Integer, Product>> getHistoryProduct(@PathVariable Long id, @RequestParam int page,
                                                              @RequestParam int size) {
        return productRepository.findRevisions(id, PageRequest.of(page, size));
    }
}
