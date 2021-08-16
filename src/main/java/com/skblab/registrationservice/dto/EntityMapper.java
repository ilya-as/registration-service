package com.skblab.registrationservice.dto;

import com.skblab.registrationservice.model.ProcessEntity;
import com.skblab.registrationservice.model.User;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {
    public User mapUserRequestDTOtoUser(RequestDTO requestDTO) {
        User user = new User();
        user.setLogin(requestDTO.getLogin());
        user.setPassword(requestDTO.getPassword());
        user.setEmail(requestDTO.getEmail());
        user.setFullName(requestDTO.getFullName());
        return user;
    }

    public ResponseDTO mapProcessEntityToResponseDTO(ProcessEntity processEntity) {

        ResponseDTO responseDTO = new ResponseDTO();

        responseDTO.setApproveStatus(processEntity.getApproveStatus());
        responseDTO.setMailStatus(processEntity.getMailStatus());
        User user = processEntity.getUser();
        if (user != null) {
            responseDTO.setEmail(user.getEmail());
            responseDTO.setLogin(user.getLogin());
        }
        return responseDTO;
    }
}
