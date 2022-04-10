package com.example.shop.service.impl

import com.example.shop.config.properties.FilePropertiesConfig
import com.example.shop.repository.ProductRepository
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class ProductServiceImplSpec extends Specification {

    def productRepository = Mock(ProductRepository)
    def filePropertiesConfig = Mock(FilePropertiesConfig)

    def productServiceImpl = new ProductServiceImpl(productRepository, filePropertiesConfig)

//    def 'Should create product'() {
//        given:
//        def product = Mock(Product)
//        def multipartFile = Mock(MultipartFile)
//
//        when:
//        productServiceImpl.create(product, multipartFile)
//
//        then:
//        1 * productRepository.save(product)
//
//    }

    def 'Should get product by id'() {
        when:
        productServiceImpl.getById(1)

        then:
        1 * productRepository.getById(1)
        0 * _
    }

    def 'Should delete by id'() {
        when:
        productServiceImpl.deleteById(1)

        then:
        1 * productRepository.deleteById(1)
        0 * _
    }

    def 'Should get page with users'() {
        given:
        def pageable = Mock(Pageable)

        when:
        productServiceImpl.getPage(pageable)

        then:
        1 * productRepository.findAll(pageable)
        0 * _
    }
}
