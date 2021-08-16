package com.skblab.registrationservice.service;

import com.skblab.registrationservice.dto.EntityMapper;
import com.skblab.registrationservice.dto.RequestDTO;
import com.skblab.registrationservice.dto.ResponseDTO;
import com.skblab.registrationservice.listener.MessageListener;
import com.skblab.registrationservice.model.ProcessEntity;
import com.skblab.registrationservice.model.User;
import com.skblab.registrationservice.model.message.Message;
import com.skblab.registrationservice.model.message.MessageId;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

@Service
public class RegistrationService {

    private ProcessService processService;
    private UserService userService;
    private EntityMapper entityMapper;

    private final Logger log = Logger.getLogger(getClass().getName());

    public RegistrationService(ProcessService processService, UserService userService, EntityMapper entityMapper, MessageListener messageListener) {
        this.processService = processService;
        this.userService = userService;
        this.entityMapper = entityMapper;
    }

    public ResponseDTO register(RequestDTO requestDTO) {
        //Добавим в DB нового пользователя
        User user = userService.addNewUser(entityMapper.mapUserRequestDTOtoUser(requestDTO));
        //Отошлем сообщение в шину и сохраним данные в базу в виде ProcessEntity
        MessageId messageId = processService.sendAndSaveMessage(Message.builder().content(user).build());

        Message message;
        try {
            //Получим сообщение из шины
            message = processService.receiveMessage(messageId.getId());
            //Установим значение approve/decline, статус email отправки=готов к отправке, обновим ProcessEntity в базе
            ProcessEntity processEntity = processService.handleMessageAndUpdateEntity(message);
            //Отошлем email и установим статус sent - отправлено
            processService.mailAndUpdateProcessEntity(processEntity);
        } catch (TimeoutException e) {
          /*  Для receive и sendMail установлена аннотация @Retryable
            но, если все равно что-то обработать не получилось - обработаем потом шедулером*/
            log.warning(e.getMessage());
        }

        return entityMapper.mapProcessEntityToResponseDTO(processService.findByMessageID(messageId.getId()));
    }

}
