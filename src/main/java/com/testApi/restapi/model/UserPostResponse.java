package com.testApi.restapi.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserPostResponse {
    private String login;
    private String password;
    private String date;

    public UserPostResponse(String login, String password){
        this.login = login;
        this.password = password;
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getDate() {
        return date;
    }
}
