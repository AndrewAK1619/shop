package com.example.shop.controller;

import com.example.shop.mapper.UserMapper;
import com.example.shop.model.dto.UserDto;
import com.example.shop.service.UserService;
import com.example.shop.validator.group.Create;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    // nazwy zawsze oryginalne dla frontu
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        // pathVariable (do zmiennych w adresie linku) to zawsze zmienna w klamrach
        return userMapper.daoToDto(userService.getById(id));
    }

    @PostMapping
    @Validated(Create.class) // włącza walidacje dla wskazanej grupy
    public UserDto saveUser(@Valid @RequestBody UserDto user) { // RequestBody dla JSON
        // dto tylko w kontrolerach powinny być i mappery
        return userMapper.daoToDto(userService.create(userMapper.dtoToDao(user)));
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @Valid @RequestBody UserDto user) {
        // adnotacja Valid włącza walidatory
        return userMapper.daoToDto(userService.update(id, userMapper.dtoToDao(user)));
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @GetMapping
    // @RequestParam >>>> ?page=0&size=10
    public Page<UserDto> getPageUser(@RequestParam int page, @RequestParam int size) {
        return userService.getPage(PageRequest.of(page, size)).map(userMapper::daoToDto);
    }
}
