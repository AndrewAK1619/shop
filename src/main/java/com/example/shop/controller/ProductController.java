package com.example.shop.controller;

import com.example.shop.mapper.ProductMapper;
import com.example.shop.model.dto.ProductDto;
import com.example.shop.service.ProductService;
import com.example.shop.validator.JpgPngValid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Validated
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
    @Operation(description = "Save Product", security = @SecurityRequirement(name = "JWT_Shop_Security"))
    public ProductDto saveProduct(@Valid @RequestPart ProductDto productDto,
                                  @RequestPart @JpgPngValid MultipartFile file) {
        return productMapper.daoToDto(productService.create(productMapper.dtoToDao(productDto), file));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductDto updateProduct(@PathVariable Long id,
                                    @Valid @RequestPart ProductDto productDto, // zmiana z body na part
                                    @RequestPart(required = false) @JpgPngValid MultipartFile file) {
        return productMapper.daoToDto(productService.update(id, productMapper.dtoToDao(productDto), file));
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
