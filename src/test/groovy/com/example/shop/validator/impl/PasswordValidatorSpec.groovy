package com.example.shop.validator.impl

import com.example.shop.model.dto.UserDto
import spock.lang.Specification

class PasswordValidatorSpec extends Specification {

    def passwordValidator = new PasswordValidator()

    def 'Should test password validator'() {
        given:
        def userDto = new UserDto(password: password, confirmPassword: confirmPassword)

        when:
        def result = passwordValidator.isValid(userDto, null)

        then:
        result == expected

        where:
        password | confirmPassword || expected
        'test1'  | 'test2'         || false
        'test3'  | 'test3'         || true
    }
}
