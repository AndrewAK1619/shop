package com.example.shop.validator.impl

import spock.lang.Specification

class JpgPngValidatorSpec extends Specification {

    def jpgPngValidator = new JpgPngValidator()

    def 'Should'() {
        when:
        jpgPngValidator.isValid(_, _)

        then:
        0 * _
    }
}
