package com.example.shop.service.impl;

import com.example.shop.model.dao.Template;
import com.example.shop.service.MailService;
import com.example.shop.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    // smtp - protokół do wysyłki maili
    // pop3 - protokół do odbioru maili

    private final JavaMailSender mailSender;
    private final TemplateService templateService;
    private final ITemplateEngine templateEngine;

    // json escape - formatowanie do json'a, aby np. html mógł być wysłany w json

    @Async
    @Override
    public void sendMail(String emailReceiver, String templateName,
                         Map<String, Object> variables,
                         String fileName, byte[] file) {
        Template template = templateService.getTemplateByName(templateName);
        Context context = new Context(Locale.getDefault(), variables);  // kontext do wygenerowania html
        String body = templateEngine.process(template.getBody(), context);  // generowanie html, body z template to z html w środku

        mailSender.send(mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true); // true pozwala wysyłać attachment
            mimeMessageHelper.setTo(emailReceiver);
            mimeMessageHelper.setSubject(template.getSubject());
            mimeMessageHelper.setText(body, true);
            if (fileName != null && file != null)
                mimeMessageHelper.addAttachment(fileName, new ByteArrayResource(file));
        });
    }
}
