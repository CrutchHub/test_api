package com.testApi.restapi.model;
import lombok.Data;


@Data
public class UserGetResponse {
    private String login;
    private String status;

    public UserGetResponse(){
        this.login = "Login1";
        this.status = "ok";
    }
}
