package com.testApi.restapi.model;
import lombok.Data;



public class UserGetResponse {
    private String login;
    private String status;

    public String getLogin() {
        return login;
    }

    public String getStatus() {
        return status;
    }

    public UserGetResponse(){
        this.login = "Login1";
        this.status = "ok";
    }
}
