package com.skblab.registrationservice.dto;

import com.skblab.registrationservice.model.enums.MailStatus;
import com.skblab.registrationservice.model.enums.ApproveStatus;
import lombok.*;

@Getter
@Setter
public class ResponseDTO {
    private String login;
    private String email;
    private ApproveStatus approveStatus;
    private MailStatus mailStatus;
}
