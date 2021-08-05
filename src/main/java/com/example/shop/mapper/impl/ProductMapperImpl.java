//package com.example.shop.mapper.impl;
//
//import com.example.shop.mapper.ProductMapper;
//import com.example.shop.model.dao.Product;
//import com.example.shop.model.dto.ProductDto;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ProductMapperImpl implements ProductMapper {
//
//    @Override
//    public Product dtoToDao(ProductDto productDto) {
//        return Product.builder()
//                .id(productDto.getId())
//                .name(productDto.getName())
//                .serialNumber(productDto.getSerialNumber())
//                .description(productDto.getDescription())
//                .build();
//    }
//
//    @Override
//    public ProductDto daoToDto(Product product) {
//        return ProductDto.builder()
//                .id(product.getId())
//                .name(product.getName())
//                .serialNumber(product.getSerialNumber())
//                .description(product.getDescription())
//                .build();
//    }
//}
