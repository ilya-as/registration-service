package com.skblab.registrationservice.service;

import com.skblab.registrationservice.model.ProcessEntity;
import com.skblab.registrationservice.model.User;
import com.skblab.registrationservice.model.enums.ApproveStatus;
import com.skblab.registrationservice.model.enums.MailStatus;
import com.skblab.registrationservice.model.mail.EmailAddress;
import com.skblab.registrationservice.model.mail.EmailContent;
import com.skblab.registrationservice.model.message.Message;
import com.skblab.registrationservice.model.message.MessageId;
import com.skblab.registrationservice.repository.ProcessEntityRepository;

import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

@Service
public class ProcessService {
    private MessagingService messagingService;
    private ProcessEntityRepository processEntityRepository;
    private SendMailer sendMailer;

    private final Logger log = Logger.getLogger(getClass().getName());

    public ProcessService(MessagingService messagingService, ProcessEntityRepository processEntityRepository, SendMailer sendMail) {
        this.messagingService = messagingService;
        this.processEntityRepository = processEntityRepository;
        this.sendMailer = sendMail;
    }

    public MessageId sendAndSaveMessage(Message message) {
        MessageId messageId = messagingService.send(message);
        ProcessEntity processEntity = new ProcessEntity();
        processEntity.setUser((User) message.getContent());
        processEntity.setMessageID(messageId.getId());
        processEntityRepository.save(processEntity);
        return messageId;
    }

    public Message receiveMessage(UUID messageId) throws TimeoutException {
        return messagingService.receive(messageId);
    }

    public ProcessEntity handleMessageAndUpdateEntity(Message incomingMessage) {
        ProcessEntity processEntity = findByMessageID(incomingMessage.getMessageId());
        processEntity.setApproveStatus(incomingMessage.getApproved() ? ApproveStatus.APPROVED : ApproveStatus.DECLINED);
        processEntity.setMailStatus(MailStatus.READY_TO_SEND);
        return processEntityRepository.save(processEntity);
    }


    public void mailAndUpdateProcessEntity(ProcessEntity processEntity) {
        User user = processEntity.getUser();

        EmailAddress emailAddress = new EmailAddress();
        emailAddress.setAddress(user.getEmail());

        EmailContent emailContent = new EmailContent();
        emailContent.setContent(String.format("Ваша регистрация %s",
                ApproveStatus.APPROVED.equals(processEntity.getApproveStatus()) ? "одобрена" : "отклонена"));

        try {
            sendMailer.sendMail(emailAddress, emailContent);
            processEntity.setMailStatus(MailStatus.SENT);
            processEntityRepository.save(processEntity);
        } catch (TimeoutException e) {
            log.warning(e.getMessage());
        }
    }

    public ProcessEntity findByMessageID(UUID uuid) {
        return processEntityRepository.findByMessageID(uuid);
    }

}
