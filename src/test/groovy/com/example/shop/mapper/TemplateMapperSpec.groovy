package com.example.shop.mapper

import com.example.shop.model.dao.Template
import com.example.shop.model.dto.TemplateDto
import spock.lang.Specification

class TemplateMapperSpec extends Specification {

    def templateMapperImpl = new TemplateMapperImpl()

    def 'Should mapping dao to dto'() {
        given:
        def template = Template.builder()
                .id(1)
                .name('DummyName')
                .subject('DummySubject')
                .body('Body')
                .build()

        when:
        def result = templateMapperImpl.daoToDto(template)

        then:
        result.id == template.id
        result.name == template.name
        result.subject == template.subject
        result.body == template.body
    }

    def 'Should mapping dto to dao'() {
        given:
        def templateDto = TemplateDto.builder()
                .id(1)
                .name('DummyName')
                .subject('DummySubject')
                .body('DummySubject')
                .build()

        when:
        def result = templateMapperImpl.dtoToDao(templateDto)

        then:
        result.id == templateDto.id
        result.name == templateDto.name
        result.subject == templateDto.subject
        result.body == templateDto.body
    }
}
