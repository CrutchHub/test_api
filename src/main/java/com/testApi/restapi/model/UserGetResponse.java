package com.testApi.restapi.model;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
public class UserGetResponse {
    private String login;
    private String status;

    public UserGetResponse(){
        this.login = "Login1";
        this.status = "ok";
    }
}
