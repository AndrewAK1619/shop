package com.example.shop.service.impl

import com.example.shop.exception.ExceededQuantityException
import com.example.shop.model.dao.Basket
import com.example.shop.model.dao.Product
import com.example.shop.model.dao.User
import com.example.shop.repository.BasketRepository
import com.example.shop.service.ProductService
import com.example.shop.service.UserService
import spock.lang.Specification

class BasketServiceImplSpec extends Specification {

    def basketRepository = Mock(BasketRepository)
    def productService = Mock(ProductService)
    def userService = Mock(UserService)

    def basketServiceImpl = new BasketServiceImpl(basketRepository, productService, userService)

    def 'Should get products'() {
        given:
        def user = Mock(User)
        def basket = Mock(Basket)
        def product = Mock(Product)

        when:
        basketServiceImpl.getProducts()

        then:
        1 * userService.getCurrentUser() >> user
        1 * user.getId() >> 1
        1 * basketRepository.findByUserId(1) >> [basket]
        1 * basket.getProduct() >> product
        1 * basket.getQuantity() >> 5
        1 * product.setQuantity(5)
        0 * _
    }

    /**
     *  =========================== addProduct ========================================
     */

    def 'Should throw ExceededQuantityException on addProduct when basket exists and available product quantity is less than add quantity'() {
        given:
        def user = Mock(User)
        def basket = Mock(Basket)
        def product = Mock(Product)

        when:
        basketServiceImpl.addProduct(1L, 5)

        then:
        1 * userService.getCurrentUser() >> user
        1 * user.getId() >> 1
        1 * basketRepository.findByProductIdAndUserId(1L, 1) >> Optional.of(basket)
        1 * basket.getProduct() >> product
        1 * product.getQuantity() >> 5
        1 * basket.getQuantity() >> 5
        0 * _
        def exception = thrown ExceededQuantityException
        exception.message == 'Not enough product quantity'
    }

    def 'Should add product when basket exists and available product quantity is more than add quantity'() {
        given:
        def user = Mock(User)
        def basket = Mock(Basket)
        def product = Mock(Product)

        when:
        basketServiceImpl.addProduct(1L, 5)

        then:
        1 * userService.getCurrentUser() >> user
        1 * user.getId() >> 1
        1 * basketRepository.findByProductIdAndUserId(1L, 1) >> Optional.of(basket)
        1 * basket.getProduct() >> product
        1 * product.getQuantity() >> 10
        2 * basket.getQuantity() >> 2
        1 * basket.setQuantity(7)
        0 * _
    }

    def 'Should throw ExceededQuantityException on addProduct when basket not exists and available product quantity is less than add quantity'() {
        given:
        def user = Mock(User)
        def product = Mock(Product)

        when:
        basketServiceImpl.addProduct(1L, 5)

        then:
        1 * userService.getCurrentUser() >> user
        1 * user.getId() >> 1
        1 * basketRepository.findByProductIdAndUserId(1L, 1) >> Optional.empty()
        1 * productService.getById(1) >> product
        1 * product.getQuantity() >> 4
        0 * _
        def exception = thrown ExceededQuantityException
        exception.message == 'Not enough product quantity'
    }

    def 'Should add product when basket not exists and available product quantity is more than add quantity'() {
        given:
        def user = Mock(User)
        def product = Mock(Product)
        def basket = Basket.builder()
                .user(user)
                .product(product)
                .quantity(5)
                .build()

        when:
        basketServiceImpl.addProduct(1L, 5)

        then:
        1 * userService.getCurrentUser() >> user
        1 * user.getId() >> 1
        1 * basketRepository.findByProductIdAndUserId(1L, 1) >> Optional.empty()
        1 * productService.getById(1) >> product
        1 * product.getQuantity() >> 12
        1 * basketRepository.save(basket)
        0 * _
    }

    /**
     * =========================== addProductOverride ========================================
     */

    def 'Should throw ExceededQuantityException on addProductOverride when basket exists and available product quantity is less than add quantity'() {
        given:
        def user = Mock(User)
        def basket = Mock(Basket)
        def product = Mock(Product)

        when:
        basketServiceImpl.addProductOverride(1L, 10)

        then:
        1 * userService.getCurrentUser() >> user
        1 * user.getId() >> 1
        1 * basketRepository.findByProductIdAndUserId(1L, 1) >> Optional.of(basket)
        1 * basket.getProduct() >> product
        1 * product.getQuantity() >> 5
        0 * _
        def exception = thrown ExceededQuantityException
        exception.message == 'Not enough product quantity'
    }

    def 'Should add product and override when basket exists and available product quantity is more than add quantity'() {
        given:
        def user = Mock(User)
        def basket = Mock(Basket)
        def product = Mock(Product)

        when:
        basketServiceImpl.addProductOverride(1L, 5)

        then:
        1 * userService.getCurrentUser() >> user
        1 * user.getId() >> 1
        1 * basketRepository.findByProductIdAndUserId(1L, 1) >> Optional.of(basket)
        1 * basket.getProduct() >> product
        1 * product.getQuantity() >> 10
        1 * basket.setQuantity(5)
        0 * _
    }

    def 'Should throw ExceededQuantityException on addProductOverride when basket not exists and available product quantity is less than add quantity'() {
        given:
        def user = Mock(User)
        def product = Mock(Product)

        when:
        basketServiceImpl.addProductOverride(1L, 5)

        then:
        1 * userService.getCurrentUser() >> user
        1 * user.getId() >> 1
        1 * basketRepository.findByProductIdAndUserId(1L, 1) >> Optional.empty()
        1 * productService.getById(1) >> product
        1 * product.getQuantity() >> 4
        0 * _
        def exception = thrown ExceededQuantityException
        exception.message == 'Not enough product quantity'
    }

    def 'Should add product and override when basket not exists and available product quantity is more than add quantity'() {
        given:
        def user = Mock(User)
        def product = Mock(Product)
        def basket = Basket.builder()
                .user(user)
                .product(product)
                .quantity(5)
                .build()

        when:
        basketServiceImpl.addProductOverride(1L, 5)

        then:
        1 * userService.getCurrentUser() >> user
        1 * user.getId() >> 1
        1 * basketRepository.findByProductIdAndUserId(1L, 1) >> Optional.empty()
        1 * productService.getById(1) >> product
        1 * product.getQuantity() >> 12
        1 * basketRepository.save(basket)
        0 * _
    }

    def 'Should delete product by id'() {
        given:
        def user = Mock(User)

        when:
        basketServiceImpl.deleteProduct(2L)

        then:
        1 * userService.getCurrentUser() >> user
        1 * user.getId() >> 1L
        1 * basketRepository.deleteByProductIdAndUserId(2L, 1L)
        0 * _
    }

    def 'Should clear basket'() {
        given:
        def user = Mock(User)

        when:
        basketServiceImpl.clearBasket()

        then:
        1 * userService.getCurrentUser() >> user
        1 * user.getId() >> 1L
        1 * basketRepository.deleteByUserId(1L)
        0 * _
    }
}
