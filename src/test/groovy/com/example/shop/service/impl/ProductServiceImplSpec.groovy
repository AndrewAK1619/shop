package com.example.shop.service.impl

import com.example.shop.config.properties.FilePropertiesConfig
import com.example.shop.helper.FileHelper
import com.example.shop.model.dao.Product
import com.example.shop.repository.ProductRepository
import org.springframework.data.domain.Pageable
import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

import java.nio.file.Paths

class ProductServiceImplSpec extends Specification {

    def productRepository = Mock(ProductRepository)
    def filePropertiesConfig = Mock(FilePropertiesConfig)
    def fileHelper = Mock(FileHelper)

    def productServiceImpl = new ProductServiceImpl(productRepository, filePropertiesConfig, fileHelper)

    def 'Should create product'() {
        given:
        def product = Mock(Product)
        def file = Mock(MultipartFile)
        def inputStream = Mock(InputStream)
        def path = Paths.get("ExampleProduct", '2.xlsx')

        when:
        productServiceImpl.create(product, file)

        then:
        1 * productRepository.save(product) >> product
        1 * filePropertiesConfig.getProduct() >> "ExampleProduct"
        1 * product.getId() >> 2
        1 * file.getOriginalFilename() >> 'ExampleFileName.xlsx'
        1 * file.getInputStream() >> inputStream
        1 * fileHelper.save(inputStream, path)
        2 * product.getPath() >> '/example/path'
        1 * product.setPath('ExampleProduct\\2.xlsx')
        0 * _
    }

    def 'Should create product and delete oldPath'() {
        given:
        def product = Mock(Product)
        def file = Mock(MultipartFile)
        def inputStream = Mock(InputStream)
        def path = Paths.get("ExampleProduct", '2.xlsx')
        def oldPath = Paths.get('/example/path')

        when:
        productServiceImpl.create(product, file)

        then:
        1 * productRepository.save(product) >> product
        1 * filePropertiesConfig.getProduct() >> "ExampleProduct"
        1 * product.getId() >> 2
        1 * file.getOriginalFilename() >> 'ExampleFileName.xlsx'
        1 * file.getInputStream() >> inputStream
        1 * fileHelper.save(inputStream, path)
        1 * product.getPath() >> '/example/path'
        1 * product.setPath('ExampleProduct\\2.xlsx')
        1 * product.getPath() >> 'ExampleProduct\\2.xlsx'
        1 * fileHelper.delete(oldPath)
        0 * _
    }

    def 'Should throw IOException on create product'() {
        given:
        def product = Mock(Product)
        def file = Mock(MultipartFile)
        def inputStream = Mock(InputStream)
        def path = Paths.get("ExampleProduct", '2.xlsx')

        when:
        productServiceImpl.create(product, file)

        then:
        1 * productRepository.save(product) >> product
        1 * filePropertiesConfig.getProduct() >> "ExampleProduct"
        1 * product.getId() >> 2
        1 * file.getOriginalFilename() >> 'ExampleFileName.xlsx'
        1 * file.getInputStream() >> inputStream
        1 * fileHelper.save(inputStream, path) >> {throw new IOException()}
        0 * _
    }

    def 'Should update product'() {
        given:
        def product = Mock(Product)
        def productDb = Mock(Product)
        def file = Mock(MultipartFile)
        def inputStream = Mock(InputStream)
        def path = Paths.get("ExampleProduct", '2.xlsx')

        when:
        productServiceImpl.update(2, product, file)

        then:
        1 * productRepository.getById(2) >> productDb
        1 * product.getName() >> 'DummyFileName'
        1 * productDb.setName('DummyFileName')
        1 * product.getSerialNumber() >> 123456789
        1 * productDb.setSerialNumber(123456789)
        1 * product.getDescription() >> 'DummyDescription'
        1 * productDb.setDescription('DummyDescription')
        1 * filePropertiesConfig.getProduct() >> "ExampleProduct"
        1 * file.getOriginalFilename() >> 'ExampleFileName.xlsx'
        1 * file.getInputStream() >> inputStream
        1 * fileHelper.save(inputStream, path)
        2 * productDb.getPath() >> '/example/path'
        1 * productDb.setPath('ExampleProduct\\2.xlsx')
        0 * _
    }

    def 'Should update product and delete oldPath'() {
        given:
        def product = Mock(Product)
        def productDb = Mock(Product)
        def file = Mock(MultipartFile)
        def inputStream = Mock(InputStream)
        def path = Paths.get("ExampleProduct", '2.xlsx')
        def oldPath = Paths.get('/example/path')

        when:
        productServiceImpl.update(2, product, file)

        then:
        1 * productRepository.getById(2) >> productDb
        1 * product.getName() >> 'DummyFileName'
        1 * productDb.setName('DummyFileName')
        1 * product.getSerialNumber() >> 123456789
        1 * productDb.setSerialNumber(123456789)
        1 * product.getDescription() >> 'DummyDescription'
        1 * productDb.setDescription('DummyDescription')
        1 * filePropertiesConfig.getProduct() >> "ExampleProduct"
        1 * file.getOriginalFilename() >> 'ExampleFileName.xlsx'
        1 * file.getInputStream() >> inputStream
        1 * fileHelper.save(inputStream, path)
        1 * productDb.getPath() >> '/example/path'
        1 * productDb.setPath('ExampleProduct\\2.xlsx')
        1 * productDb.getPath() >> 'ExampleProduct\\2.xlsx'
        1 * fileHelper.delete(oldPath)
        0 * _
    }

    def 'Should throw IOException on update product'() {
        given:
        def product = Mock(Product)
        def productDb = Mock(Product)
        def file = Mock(MultipartFile)
        def inputStream = Mock(InputStream)
        def path = Paths.get("ExampleProduct", '2.xlsx')

        when:
        productServiceImpl.update(2, product, file)

        then:
        1 * productRepository.getById(2) >> productDb
        1 * product.getName() >> 'DummyFileName'
        1 * productDb.setName('DummyFileName')
        1 * product.getSerialNumber() >> 123456789
        1 * productDb.setSerialNumber(123456789)
        1 * product.getDescription() >> 'DummyDescription'
        1 * productDb.setDescription('DummyDescription')
        1 * filePropertiesConfig.getProduct() >> "ExampleProduct"
        1 * file.getOriginalFilename() >> 'ExampleFileName.xlsx'
        1 * file.getInputStream() >> inputStream
        1 * fileHelper.save(inputStream, path) >> {throw new IOException()}
        0 * _
    }

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
