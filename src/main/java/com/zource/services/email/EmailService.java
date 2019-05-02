/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

package com.zource.services.email;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendSimpleMessage(String to,
                           String subject,
                           String text);

    void sendSimpleMessageUsingTemplate(String to,
                                        String subject,
                                        SimpleMailMessage template,
                                        String... templateArgs);

    void sendMessageWithAttachment(String to,
                                   String subject,
                                   String text,
                                   String pathToAttachment);

    void prepareAndSend(String recipient, String message);
}