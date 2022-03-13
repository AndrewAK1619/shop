package com.example.shop.service.impl;

import com.example.shop.model.dao.Template;
import com.example.shop.service.MailService;
import com.example.shop.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    // smtp - protokół do wysyłki maili
    // pop3 - protokół do odbioru maili

    private final JavaMailSender mailSender;
    private final TemplateService templateService;

    @Async
    @Override
    public void sendMail(String emailReceiver, String templateName) {
        Template template = templateService.getTemplateByName(templateName);
        mailSender.send(mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(emailReceiver);
            mimeMessageHelper.setSubject(template.getSubject());
            mimeMessageHelper.setText(template.getBody(), true);
        });
    }
}
