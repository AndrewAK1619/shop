package com.example.shop.controller;

import com.example.shop.flyweight.generic.GenericFactory;
import com.example.shop.flyweight.generic.strategy.mail.MailGeneratorStrategy;
import com.example.shop.flyweight.model.MailType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mails")
public class MailController {

    private final GenericFactory<MailType, MailGeneratorStrategy> genericFactory;

    @GetMapping
    public void getMail(@RequestParam MailType mailType) {
        genericFactory.getStrategyByType(mailType).generateMail();
    }
}
