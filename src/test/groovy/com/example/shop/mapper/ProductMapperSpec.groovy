package com.example.shop.mapper

import com.example.shop.model.dao.Product
import com.example.shop.model.dto.ProductDto
import spock.lang.Specification

class ProductMapperSpec extends Specification {

    def productMapperImpl = new ProductMapperImpl()

    def 'Should mapping dto to dao'() {
        given:
        def productDto = ProductDto.builder()
                .id(1)
                .name("DummyName")
                .serialNumber(222333)
                .quantity(3)
                .price(44.4)
                .description('DummyDescription')
                .revisionNumber(3)
                .path('/example')
                .build()

        when:
        def result = productMapperImpl.dtoToDao(productDto)

        then:
        result.id == productDto.id
        result.name == productDto.name
        result.serialNumber == productDto.serialNumber
        result.quantity == productDto.quantity
        result.price == productDto.price
        result.description == productDto.description
        result.path == productDto.path
    }

    def 'Should mapping dao to dto'() {
        given:
        def product = Product.builder()
                .id(1)
                .name('DummyName')
                .serialNumber(123433)
                .quantity(2)
                .price(434.43)
                .description('DummyDescription')
                .path('/path')
                .build()

        when:
        def result = productMapperImpl.daoToDto(product)

        then:
        result.id == product.id
        result.name == product.name
        result.serialNumber == product.serialNumber
        result.quantity == product.quantity
        result.price == product.price
        result.description == product.description
        result.path == product.path
        result.revisionNumber == null
        result.revisionType == null
    }

    def 'Should mapping dao list to dto list'() {
        given:
        def product = Product.builder()
                .id(1)
                .name('DummyName')
                .serialNumber(123433)
                .quantity(2)
                .price(434.43)
                .description('DummyDescription')
                .path('/path')
                .build()
        def products = [product] as List

        when:
        def result = productMapperImpl.daoListToDtoList(products)

        then:
        result.size() == 1
        result[0].id == products[0].id
        result[0].name == products[0].name
        result[0].serialNumber == products[0].serialNumber
        result[0].quantity == products[0].quantity
        result[0].price == products[0].price
        result[0].description == products[0].description
        result[0].path == products[0].path
    }
}
