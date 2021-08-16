package com.skblab.registrationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {

    @NotBlank(message = "Please input a login")
    private String login;

    @Size(min = 5, message = "Please input a password at a minimum 5 symbols or more")
    private String password;

    @Email(message = "Please input a valid e-mail")
    @NotBlank(message = "Please input a e-mail")
    private String email;

    @NotBlank(message = "Please input a name")
    private String fullName;
}
