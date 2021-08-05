package com.example.shop.model.dto;

import com.example.shop.validator.PasswordValid;
import com.example.shop.validator.group.Create;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

// JsonInclude >> jeżeli jakaś wartość będzie null to nie zostanie dodane do odpowiedzi
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@PasswordValid(groups = Create.class)
// dla naszej adnotacji walidacyjnej wskazujemy grupę do walidacji, kiedy ma zostać uruchomiona
public class UserDto {
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Past   // musi być wcześniejsza od aktualnej daty
    private LocalDate birthDate;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    private String confirmPassword;
}
