package com.example.shop.controller;

import com.example.shop.model.dao.User;
import com.example.shop.model.dto.UserDto;
import com.example.shop.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc // pozwala wstrzyknąć mockMvc pozwalając wysyłać requesty na nasze endpointy
@ActiveProfiles("test") // włączyć profil test, np. żeby uniknąc z metod gdzie korzystają z zewnętrznych api,
// do obsługi plików, mokuje czytanie plików
@TestPropertySource(locations = "classpath:application-test.yml")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveUser() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserDto.builder()
                                .firstName("DummyFirstName")
                                .lastName("DummyLastName")
                                .birthDate(LocalDate.of(2000, 5, 5))
                                .email("DummyMail@example.com")
                                .password("example")
                                .confirmPassword("example")
                                .build())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("DummyFirstName"))
                .andExpect(jsonPath("$.lastName").value("DummyLastName"))
                .andExpect(jsonPath("$.birthDate").value("2000-05-05"))
                .andExpect(jsonPath("$.email").value("DummyMail@example.com"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.confirmPassword").doesNotExist());
    }

    @Test
    void shouldNotSaveUserWhenDuplicated() throws Exception {
        userRepository.save(User.builder()
                .firstName("DummyFirstName")
                .lastName("DummyLastName")
                .birthDate(LocalDate.of(2000, 5, 5))
                .email("DummyMail@example.com")
                .password("12345789")
                .build());

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserDto.builder()
                                .firstName("DummyFirstName")
                                .lastName("DummyLastName")
                                .birthDate(LocalDate.of(2000, 5, 5))
                                .email("DummyMail@example.com")
                                .password("example")
                                .confirmPassword("example")
                                .build())))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Entity already exist"));
    }

    @Test
    void shouldNotSaveUserWhenNotValidParameters() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserDto.builder()
                                .firstName(null)
                                .lastName("     ")
                                .birthDate(LocalDate.now().plusDays(5))
                                .email("exampleWrongEmail")
                                .password("example")
                                .confirmPassword("example")
                                .build())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[*].field",
                        containsInAnyOrder("firstName", "lastName", "birthDate", "email")))
                .andExpect(jsonPath("$[*].message",
                        containsInAnyOrder("must not be blank", "must not be blank", "must be a past date", "must be a well-formed email address")));
    }

    @Test
    void shouldNotSaveUserWhenPasswordsAreIncorrect() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserDto.builder()
                                .firstName("DummyFirstName")
                                .lastName("DummyLastName")
                                .birthDate(LocalDate.of(2000, 5, 5))
                                .email("DummyMail@example.com")
                                .password("example")
                                .confirmPassword("example123")
                                .build())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("saveUser.user: Password - should be the same"));
    }

    @Test
    void shouldNotDeleteWhenUserNotAuthorized() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());

    }

    @Test
    @WithMockUser
    void shouldNotDeleteWhenWithoutAdminRole() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldNotDeleteWhenHasAdminRoleAndCanDelete() throws Exception {
        userRepository.save(User.builder()
                .firstName("DummyFirstName")
                .lastName("DummyLastName")
                .birthDate(LocalDate.of(2000, 5, 5))
                .email("DummyMail@example.com")
                .password("12345789")
                .build());

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

    }
}
