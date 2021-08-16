package com.skblab.registrationservice.controller;

import com.skblab.registrationservice.dto.ResponseDTO;
import com.skblab.registrationservice.dto.RequestDTO;
import com.skblab.registrationservice.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegistrationController {

    private RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@Valid @RequestBody RequestDTO requestDTO) {
        return new ResponseEntity<>(registrationService.register(requestDTO), HttpStatus.CREATED);
    }

}
