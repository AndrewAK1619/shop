package com.example.shop.flyweight.generic.strategy.mail;

import com.example.shop.flyweight.generic.strategy.GenericStrategy;
import com.example.shop.flyweight.model.MailType;

public interface MailGeneratorStrategy extends GenericStrategy<MailType> {

    byte[] generateMail();
}
