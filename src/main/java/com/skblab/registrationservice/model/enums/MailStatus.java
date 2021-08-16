package com.skblab.registrationservice.model.enums;

import lombok.Getter;

@Getter
public enum MailStatus {
    SENT,
    READY_TO_SEND,
    NOT_PROCESSED;
}
