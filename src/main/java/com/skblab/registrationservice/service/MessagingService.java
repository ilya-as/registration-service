package com.skblab.registrationservice.service;

import com.skblab.registrationservice.model.message.Message;
import com.skblab.registrationservice.model.message.MessageId;


import java.util.UUID;
import java.util.concurrent.TimeoutException;

public interface MessagingService {
    /**
     * Отправка сообщения в шину.
     *
     * @param msg сообщение для отправки.
     * @return идентификатор отправленного сообщения (correlationId)
     */
    <T> MessageId send(Message<T> msg);

    /**
     * Встает на ожидание ответа по сообщению с messageId.
     * <p>
     * Редко, но может кинуть исключение по таймауту.
     *
     * @param messageId идентификатор сообщения, на которое ждем ответ.
     * @return Тело ответа.
     */
    <T> Message<T> receive(UUID messageId) throws TimeoutException;

    /**
     * Отправляем сообщение и ждем на него ответ.
     *
     * @param request тело запроса.
     * @return тело ответа.
     */
    <R, A> Message<A> doRequest(Message<R> request) throws TimeoutException;
}
