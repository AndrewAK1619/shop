package com.example.shop.controller

import com.example.shop.model.dto.ProductDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
class ProductControllerSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    @Autowired
    ObjectMapper objectMapper

    def 'Should '() {
        given:
        def product = new MockMultipartFile('productDto', '', MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsBytes(ProductDto.builder()
                        .name('Game')
                        .serialNumber(123123)
                        .quantity(120)
                        .price(170)
                        .description("dummyDesc")
                        .build()))

        def file = new MockMultipartFile('file', 'DummyFileName.xlsx', MediaType.APPLICATION_OCTET_STREAM_VALUE,
                new byte[0])

        expect:
        mockMvc.perform(MockMvcRequestBuilders.multipart('/api/products')
                .file(product)
                .file(file)
                .with({ prcessor -> prcessor.setMethod('POST') }))
                .andExpect(status().isCreated())
    }
}
