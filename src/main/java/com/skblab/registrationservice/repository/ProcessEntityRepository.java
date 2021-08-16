package com.skblab.registrationservice.repository;

import com.skblab.registrationservice.model.ProcessEntity;
import com.skblab.registrationservice.model.enums.ApproveStatus;

import com.skblab.registrationservice.model.enums.MailStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface ProcessEntityRepository extends JpaRepository<ProcessEntity, Long> {

    List<ProcessEntity> findAllByApproveStatus(ApproveStatus approveStatus);
    List<ProcessEntity> findAllByMailStatus(MailStatus mailStatus);
    ProcessEntity findByMessageID(UUID uuid);
}
