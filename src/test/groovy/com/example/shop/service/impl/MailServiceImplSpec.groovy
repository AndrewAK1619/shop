package com.example.shop.service.impl

import com.example.shop.model.dao.Template
import com.example.shop.service.TemplateService
import org.springframework.mail.javamail.JavaMailSender
import org.thymeleaf.ITemplateEngine
import org.thymeleaf.context.Context
import spock.lang.Specification

class MailServiceImplSpec extends Specification {

    def mailSender = Mock(JavaMailSender)
    def templateService = Mock(TemplateService)
    def templateEngine = Mock(ITemplateEngine)

    def mailServiceImpl = new MailServiceImpl(mailSender, templateService, templateEngine)

    def 'Should send mail'() {
        given:
        def email = 'example@example.com'
        def templateName = 'DummyTemplateName'
        def fileName = 'DummyFileName'
        Map<String, Object> variables = new HashMap<>()

        def template = Mock(Template)

        when:
        mailServiceImpl.sendMail(email, templateName, variables, fileName)

        then:
        1 * templateService.getTemplateByName(templateName) >> template
        1 * template.getBody() >> 'Body'
        1 * templateEngine.process('Body', _ as Context) >> 'Body'
        1 * mailSender.send(_)
        0 * _
    }
}
