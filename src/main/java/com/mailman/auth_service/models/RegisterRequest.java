package com.mailman.auth_service.models;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    private String userName;
    private String userPassword;
}
