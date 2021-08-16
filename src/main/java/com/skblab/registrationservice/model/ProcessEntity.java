package com.skblab.registrationservice.model;

import com.skblab.registrationservice.model.enums.ApproveStatus;
import com.skblab.registrationservice.model.enums.MailStatus;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter

@Entity
public class ProcessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private UUID messageID;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Enumerated(EnumType.STRING)
    private ApproveStatus approveStatus;

    @Enumerated(EnumType.STRING)
    private MailStatus mailStatus;

    public ProcessEntity() {
        this.approveStatus = ApproveStatus.NOT_PROCESSED;
        this.mailStatus = MailStatus.NOT_PROCESSED;
    }
}
