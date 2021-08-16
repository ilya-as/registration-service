package com.skblab.registrationservice.scheduler;

import com.skblab.registrationservice.model.ProcessEntity;
import com.skblab.registrationservice.model.enums.ApproveStatus;
import com.skblab.registrationservice.model.enums.MailStatus;
import com.skblab.registrationservice.model.message.Message;
import com.skblab.registrationservice.repository.ProcessEntityRepository;
import com.skblab.registrationservice.service.ProcessService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

@Component
public class ProcessScheduler {

    private ProcessEntityRepository processEntityRepository;
    private ProcessService processService;

    private final Logger log = Logger.getLogger(getClass().getName());

    public ProcessScheduler(ProcessEntityRepository processEntityRepository, ProcessService processService) {
        this.processEntityRepository = processEntityRepository;
        this.processService = processService;
    }


    public void receiveAndHandleMessage(UUID messageID) {
        Message message;
        try {
            message = processService.receiveMessage(messageID);
        } catch (TimeoutException e) {
            log.warning(e.getMessage());
            return;
        }
        processService.handleMessageAndUpdateEntity(message);
    }

    @Scheduled(fixedDelayString = "PT5M")
    public void processScheduler() {
        List<ProcessEntity> entityList = processEntityRepository.findAllByApproveStatus(ApproveStatus.NOT_PROCESSED);
        for (ProcessEntity entity : entityList) {
            receiveAndHandleMessage(entity.getMessageID());
        }

        entityList = processEntityRepository.findAllByMailStatus(MailStatus.READY_TO_SEND);
        for (ProcessEntity entity : entityList) {
            processService.mailAndUpdateProcessEntity(entity);
        }
    }
}