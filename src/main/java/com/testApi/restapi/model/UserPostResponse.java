package com.testApi.restapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class UserPostResponse {

    @NotBlank(message = "Логин не заполнен")
    @Size(min = 4, max = 20, message =  "Логин должен быть не меньше 4 и не больше 20 символов")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Логин должен быть только из букв, цифр и нижних подчеркиваний")
    private String login;

    @NotBlank(message = "Пароль не заполнен")
    @Size(min = 4, max = 20, message = "Пароль должен быть не меньше 4 и не больше 20 символов")
    private String password;
    private String date;

    @JsonCreator
    public UserPostResponse(@JsonProperty("login") String login, @JsonProperty("password") String password){
        this.login = login;
        this.password = password;
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
