package com.example.shop.controller;

import com.example.shop.mapper.UserMapper;
import com.example.shop.model.dto.UserDto;
import com.example.shop.service.UserService;
import com.example.shop.validator.group.Create;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated // włącza adnotacje @Validated do metod
@RestController
// restController ma w sobie responseBody a controller nie ma,
// dzięki temu może zwracać JSON
@RequiredArgsConstructor // tylko konstruktor dla finalnych zmiennych
@RequestMapping("/api/users") // zawsze api
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/current")
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Get Current User", security = @SecurityRequirement(name = "JWT_Shop_Security"))
    public UserDto getCurrentUser() {
        return userMapper.daoToDto(userService.getCurrentUser());
    }

    // nazwy zawsze oryginalne dla frontu
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated() && (hasRole('ADMIN') || @securityService.hasAccessToUser(#id))")
    @Operation(description = "Get User By Id", security = @SecurityRequirement(name = "JWT_Shop_Security"))
    public UserDto getUserById(@PathVariable Long id) {
        // pathVariable (do zmiennych w adresie linku) to zawsze zmienna w klamrach
        return userMapper.daoToDto(userService.getById(id));
    }

    @PostMapping
    @Validated(Create.class) // włącza walidacje dla wskazanej grupy
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto saveUser(@Valid @RequestBody UserDto user) { // RequestBody dla JSON
        // dto tylko w kontrolerach powinny być i mappery
        return userMapper.daoToDto(userService.create(userMapper.dtoToDao(user)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated() && (hasRole('ADMIN') || @securityService.hasAccessToUser(#id))")
    @Operation(description = "Update User", security = @SecurityRequirement(name = "JWT_Shop_Security"))
    public UserDto updateUser(@PathVariable Long id, @Valid @RequestBody UserDto user) {
        // adnotacja Valid włącza walidatory
        return userMapper.daoToDto(userService.update(id, userMapper.dtoToDao(user)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Delete User By Id", security = @SecurityRequirement(name = "JWT_Shop_Security"))
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Get Page User", security = @SecurityRequirement(name = "JWT_Shop_Security"))
    // @RequestParam >>>> ?page=0&size=10
    public Page<UserDto> getPageUser(@RequestParam int page, @RequestParam int size) {
        return userService.getPage(PageRequest.of(page, size)).map(userMapper::daoToDto);
    }
}
