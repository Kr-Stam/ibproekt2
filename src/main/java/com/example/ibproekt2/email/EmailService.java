package com.example.ibproekt2.email;

import jakarta.mail.MessagingException;

public interface EmailService {

    void sendMail(final AbstractEmailContext email) throws MessagingException;
}