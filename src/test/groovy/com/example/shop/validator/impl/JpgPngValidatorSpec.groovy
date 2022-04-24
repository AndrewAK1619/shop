package com.example.shop.validator.impl

import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

class JpgPngValidatorSpec extends Specification {

    def jpgPngValidator = new JpgPngValidator()

    def 'Should return true when extension is valid'() {
        given:
        def multipartFile = Mock(MultipartFile)

        when:
        def result = jpgPngValidator.isValid(multipartFile, null)

        then:
        1 * multipartFile.isEmpty() >> isEmpty
        getOriginalFileNameTimes * multipartFile.getOriginalFilename() >> fileName
        0 * _
        result == expected

        where:
        isEmpty | fileName             || expected | getOriginalFileNameTimes
        false   | 'DummyFileName.PNG'  || true     | 2
        false   | 'DummyFileName.JPG'  || true     | 2
        false   | 'DummyFileName.JPEG' || true     | 2
        false   | 'DummyFileName'      || false    | 2
        true    | 'DummyFileName.PNG'  || false    | 0
        false   | null                 || false    | 1
    }
}
