package com.example.shop.mapper;

import com.example.shop.model.dao.Product;
import com.example.shop.model.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends AuditableMapper<Product, ProductDto> {

    Product dtoToDao(ProductDto productDto);

    ProductDto daoToDto(Product product);
}
