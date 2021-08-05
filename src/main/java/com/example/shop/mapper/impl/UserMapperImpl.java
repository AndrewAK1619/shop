//package com.example.shop.mapper.impl;
//
//import com.example.shop.mapper.UserMapper;
//import com.example.shop.model.dao.User;
//import com.example.shop.model.dto.UserDto;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserMapperImpl implements UserMapper {
//
//    @Override
//    public User dtoToDao(UserDto userDto) {
//        return User.builder()
//                .id(userDto.getId())
//                .firstName(userDto.getFirstName())
//                .lastName(userDto.getLastName())
//                .birthDate(userDto.getBirthDate())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .build();
//    }
//
//    @Override
//    public UserDto daoToDto(User user) {
//        return UserDto.builder()
//                .id(user.getId())
//                .firstName(user.getFirstName())
//                .lastName(user.getLastName())
//                .birthDate(user.getBirthDate())
//                .email(user.getEmail())
//                // bez passworda bo nigdy nie powinniśmy wysyłać hasła klientowi
//                .build();
//    }
//}
