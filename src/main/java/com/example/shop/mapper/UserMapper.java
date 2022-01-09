package com.example.shop.mapper;

import com.example.shop.model.dao.Role;
import com.example.shop.model.dao.User;
import com.example.shop.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper extends AuditableMapper<User, UserDto> {

    @Mapping(target = "roles", ignore = true)
    User dtoToDao(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapUserRoles")
    UserDto daoToDto(User user);

    @Named("mapUserRoles")
    default List<String> mapUserRoles(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}
