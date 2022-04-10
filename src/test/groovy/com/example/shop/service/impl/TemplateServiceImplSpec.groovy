package com.example.shop.service.impl

import com.example.shop.model.dao.Template
import com.example.shop.repository.TemplateRepository
import org.springframework.data.domain.Pageable
import spock.lang.Specification

import javax.persistence.EntityNotFoundException

class TemplateServiceImplSpec extends Specification {

    def templateRepository = Mock(TemplateRepository)

    def templateServiceImpl = new TemplateServiceImpl(templateRepository)

    def 'Should get template by name if exists'() {
        given:
        def template = Mock(Template)

        when:
        templateServiceImpl.getTemplateByName('DummyTemplateName')

        then:
        1 * templateRepository.findByName('DummyTemplateName') >> Optional.of(template)
        0 * _
    }

    def 'Should throw EntityNotFoundException on getTemplateByName when template not exists'() {
        given:
        def template = Mock(Template)

        when:
        templateServiceImpl.getTemplateByName('DummyTemplateName')

        then:
        1 * templateRepository.findByName('DummyTemplateName') >> Optional.empty()
        0 * _
        def exception = thrown EntityNotFoundException
        exception.message == 'DummyTemplateName'
    }

    def 'Should create new template'() {
        def template = new Template()

        when:
        templateServiceImpl.create(template)

        then:
        1 * templateRepository.save(template) >> template
        0 * _
    }

    def 'Should update template'() {
        def template = Mock(Template)
        def templateDb = Mock(Template)

        when:
        templateServiceImpl.update(1L, template)

        then:
        1 * templateRepository.getById(1L) >> templateDb
        1 * template.getName() >> 'DummyName'
        1 * templateDb.setName('DummyName')
        1 * template.getSubject() >> 'DummySubject'
        1 * templateDb.setSubject('DummySubject')
        1 * template.getBody() >> 'DummyBody'
        1 * templateDb.setBody('DummyBody')
        0 * _
    }

    def 'Should get template by id'() {
        when:
        templateServiceImpl.getById(1)

        then:
        1 * templateRepository.getById(1)
        0 * _
    }

    def 'Should delete template by id'() {
        when:
        templateServiceImpl.deleteById(1)

        then:
        1 * templateRepository.deleteById(1)
        0 * _
    }

    def 'Should get page with templates'() {
        given:
        def pageable = Mock(Pageable)

        when:
        templateServiceImpl.getPage(pageable)

        then:
        1 * templateRepository.findAll(pageable)
        0 * _
    }
}
