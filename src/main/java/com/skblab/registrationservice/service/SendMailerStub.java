package com.skblab.registrationservice.service;

import com.skblab.registrationservice.model.mail.EmailAddress;
import com.skblab.registrationservice.model.mail.EmailContent;
import lombok.SneakyThrows;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

@Component
public class SendMailerStub implements SendMailer {

    private final Logger log = Logger.getLogger(getClass().getName());

    @Override
    @Retryable(value = TimeoutException.class, maxAttempts = 5, backoff = @Backoff(delay = 100))
    public void sendMail(EmailAddress toAddress, EmailContent messageBody) throws TimeoutException {
        if (shouldThrowTimeout()) {
            sleep();

            throw new TimeoutException("Timeout!");
        }

        if (shouldSleep()) {
            sleep();
        }

        // ok.
        log.info(String.format("Message sent to %s, body %s.", toAddress, messageBody));
    }

    @SneakyThrows
    private static void sleep() {
        Thread.sleep(TimeUnit.MINUTES.toMillis(1));
    }

    private static boolean shouldSleep() {
        return new Random().nextInt(10) == 1;
    }

    private static boolean shouldThrowTimeout() {
        return new Random().nextInt(10) == 1;
    }
}
