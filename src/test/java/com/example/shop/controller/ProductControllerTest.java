//package com.example.shop.controller;
//
//import com.example.shop.model.dto.ProductDto;
//import com.example.shop.repository.UserRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@Transactional
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//@TestPropertySource(locations = "classpath:application-test.yml")
//class ProductControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    void shouldSaveProduct() throws Exception {
//        var product = new MockMultipartFile("productDto", "", MediaType.APPLICATION_JSON_VALUE,
//                objectMapper.writeValueAsBytes(ProductDto.builder()
//                        .name("Game")
//                        .serialNumber(123123L)
//                        .quantity(120)
//                        .price(170)
//                        .description("dummyDesc")
//                        .build()));
//
//        var file = new MockMultipartFile("file", "DummyFileName.jpeg", MediaType.APPLICATION_OCTET_STREAM_VALUE,
//                new byte[1]);
//
//        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/products")
//                        .file(product)
//                        .file(file)
//                        .with(processor -> {
//                                    processor.setMethod("POST");
//                                    return processor;
//                                }
//                        ))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").exists())
//                .andExpect(jsonPath("$.name").value("Game"))
//                .andExpect(jsonPath("$.serialNumber").value(123123))
//                .andExpect(jsonPath("$.quantity").value(120))
//                .andExpect(jsonPath("$.price").value(170))
//                .andExpect(jsonPath("$.description").value("dummyDesc"))
//                .andExpect(jsonPath("$.path").value("/dummy/example"));
//    }
//}
