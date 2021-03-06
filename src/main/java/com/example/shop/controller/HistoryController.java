package com.example.shop.controller;

import com.example.shop.mapper.HistoryMapper;
import com.example.shop.model.dto.ProductDto;
import com.example.shop.model.dto.UserDto;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/history")
public class HistoryController {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final HistoryMapper historyMapper;

    @GetMapping("/users/{id}")
    @PreAuthorize("isAuthenticated() && (hasRole('ADMIN') || @securityService.hasAccessToUser(#id))")
    @Operation(description = "Get History User", security = @SecurityRequirement(name = "JWT_Shop_Security"))
    public Page<UserDto> getHistoryUser(@PathVariable Long id, @RequestParam int page,
                                        @RequestParam int size) {
        return userRepository.findRevisions(id, PageRequest.of(page, size)).map(historyMapper::revisionToUserDto);
    }

    @GetMapping("products/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Get History Product", security = @SecurityRequirement(name = "JWT_Shop_Security"))
    public Page<ProductDto> getHistoryProduct(@PathVariable Long id, @RequestParam int page,
                                              @RequestParam int size) {
        return productRepository.findRevisions(id, PageRequest.of(page, size)).map(historyMapper::revisionToProductDto);
    }
}
