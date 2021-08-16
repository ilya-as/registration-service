package com.skblab.registrationservice.service;

import com.skblab.registrationservice.model.mail.EmailAddress;
import com.skblab.registrationservice.model.mail.EmailContent;

import java.util.concurrent.TimeoutException;

/**
 * Ориентировочный интерфейс мейлера.
 */
public interface SendMailer {
    void sendMail(EmailAddress toAddress, EmailContent messageBody) throws TimeoutException;
}
