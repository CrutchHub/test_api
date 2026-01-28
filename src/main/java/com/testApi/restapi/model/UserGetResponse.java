package com.testApi.restapi.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserGetResponse(
        String login,
        String status
) {
    private static final UserGetResponse INSTANCE = new UserGetResponse("Login1", "ok");

    public static UserGetResponse getInstance() {
        return INSTANCE;
    }
}
