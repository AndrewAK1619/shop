package com.example.shop.mapper;

import com.example.shop.model.dao.User;
import com.example.shop.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.history.Revision;

@Mapper(componentModel = "spring")
public interface HistoryMapper {

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.firstName", target = "firstName")
    @Mapping(source = "entity.lastName", target = "lastName")
    @Mapping(source = "entity.birthDate", target = "birthDate")
    @Mapping(source = "entity.email", target = "email")
    @Mapping(source = "requiredRevisionNumber", target = "revisionNumber")
    UserDto revisionToUserDto(Revision<Integer, User> revision);

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.name", target = "name")
    @Mapping(source = "entity.serialNumber", target = "serialNumber")
    @Mapping(source = "entity.quantity", target = "quantity")
    @Mapping(source = "entity.price", target = "price")
    @Mapping(source = "entity.description", target = "description")
    @Mapping(source = "requiredRevisionNumber", target = "revisionNumber")
    UserDto revisionToProductDto(Revision<Integer, User> revision);
}
