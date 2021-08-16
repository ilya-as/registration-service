package com.skblab.registrationservice.model.message;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class MessageId {
    private UUID id;
}
