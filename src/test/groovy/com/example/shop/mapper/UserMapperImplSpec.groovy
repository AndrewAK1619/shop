package com.example.shop.mapper

import com.example.shop.model.dao.Role
import com.example.shop.model.dao.User
import com.example.shop.model.dto.UserDto
import spock.lang.Specification

import java.time.LocalDate

class UserMapperImplSpec extends Specification {

    def userMapperImpl = new UserMapperImpl()

    def 'Should mapping dto to dao'() {
        given:
        def userDto = UserDto.builder()
                .id(1)
                .firstName('DummyFirstName')
                .lastName('DummyLastName')
                .birthDate(LocalDate.of(2011, 5, 5))
                .email('example@example.com')
                .password('example')
                .confirmPassword('example')
                .revisionNumber(2)
                .roles(['ROLE_ADMIN'])
                .build()

        when:
        def result = userMapperImpl.dtoToDao(userDto)

        then:
        result.id == userDto.id
        result.firstName == userDto.firstName
        result.lastName == userDto.lastName
        result.birthDate == userDto.birthDate
        result.email == userDto.email
        result.password == userDto.password
        result.activatedToken == null
        result.roles == null
    }

    def 'Should mapping dao to dto'() {
        given:
        def role = new Role(1, 'ROLE_ADMIN')

        def user = User.builder()
                .id(1)
                .firstName('DummyFirstName')
                .lastName('DummyLastName')
                .birthDate(LocalDate.of(2011, 5, 5))
                .email('example@example.com')
                .password('example')
                .activatedToken('gdsa%d6s7afhakDsadf')
                .roles([role] as Set<Role>)
                .build()

        when:
        def result = userMapperImpl.daoToDto(user)

        then:
        result.id == user.id
        result.firstName == user.firstName
        result.lastName == user.lastName
        result.birthDate == user.birthDate
        result.email == user.email
        result.password == null
        result.roles == ['ROLE_ADMIN']
        result.confirmPassword == null
        result.revisionNumber == null
    }
}
