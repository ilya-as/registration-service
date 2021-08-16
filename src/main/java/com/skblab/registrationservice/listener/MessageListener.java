package com.skblab.registrationservice.listener;

import com.skblab.registrationservice.model.User;
import com.skblab.registrationservice.model.message.Message;

/**
 * Опциональный интерфейс для лисенеров.
 * Необязательно реализовывать всю инфраструктуру по регистрации и обработке, достаточно и тестов.
 *
 * @param <T> тип сообщений, которые будем слушать.
 */
public interface MessageListener {
    void handleMessage(Message<User> incomingMessage);
}
