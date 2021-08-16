package com.skblab.registrationservice.exception;

public class UserLoginExistsException extends RuntimeException {

    public UserLoginExistsException(String login) {
        super(String.format("User not unique with login : %s", login));
    }
}
