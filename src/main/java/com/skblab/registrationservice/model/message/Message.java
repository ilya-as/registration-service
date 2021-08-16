package com.skblab.registrationservice.model.message;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Message<T> {

    private UUID messageId;
    private T content;
    private Boolean approved;
}
