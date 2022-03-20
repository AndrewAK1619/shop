package com.example.shop.service;

import java.util.Map;

public interface MailService {

    void sendMail(String emailReceiver, String templateName, Map<String, Object> variables);
}
