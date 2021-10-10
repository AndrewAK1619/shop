package com.example.shop.flyweight.generic.strategy.mail.impl;

import com.example.shop.flyweight.generic.strategy.mail.MailGeneratorStrategy;
import com.example.shop.flyweight.model.MailType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReminderMailGenerator implements MailGeneratorStrategy {

    @Override
    public MailType getType() {
        return MailType.REMINDER;
    }

    @Override
    public byte[] generateMail() {
        log.info("Generate REMINDER");
        return new byte[0];
    }
}
