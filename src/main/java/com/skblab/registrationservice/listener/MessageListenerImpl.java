package com.skblab.registrationservice.listener;

import com.skblab.registrationservice.model.ProcessEntity;
import com.skblab.registrationservice.model.User;
import com.skblab.registrationservice.model.message.Message;
import com.skblab.registrationservice.service.ProcessService;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class MessageListenerImpl implements MessageListener {

    private ProcessService processService;

    private final Logger log = Logger.getLogger(getClass().getName());

    public MessageListenerImpl(ProcessService processService) {
        this.processService = processService;
    }

    @Override
    public void handleMessage(Message<User> incomingMessage) {
        ProcessEntity processEntity = processService.handleMessageAndUpdateEntity(incomingMessage);
        processService.mailAndUpdateProcessEntity(processEntity);
    }

}
