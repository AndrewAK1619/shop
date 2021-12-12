package com.example.shop.controller;

import com.example.shop.mapper.ProductMapper;
import com.example.shop.model.dto.ProductDto;
import com.example.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productMapper.daoToDto(productService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ProductDto saveProduct(@Valid @RequestBody ProductDto productDto) {
        return productMapper.daoToDto(productService.create(productMapper.dtoToDao(productDto)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductDto updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
        return productMapper.daoToDto(productService.update(id, productMapper.dtoToDao(productDto)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping
    public Page<ProductDto> getProductPage(@RequestParam int page, @RequestParam int size) {
        return productService.getPage(PageRequest.of(page, size)).map(productMapper::daoToDto);
    }
}
