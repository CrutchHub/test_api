package com.testApi.restapi.model;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserGetResponse {
    private String login;
    private String status;

    public UserGetResponse(){
        this.login = "Login1";
        this.status = "ok";
    }
}
