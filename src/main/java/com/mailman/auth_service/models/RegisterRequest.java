package com.mailman.auth_service.models;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPassword() {
        return userPassword;
    }

    private String userName;
    private String userPassword;
}
