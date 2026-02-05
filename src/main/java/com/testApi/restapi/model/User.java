package com.testApi.restapi.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    @NotBlank(message = "Логин не заполнен")
    @Size(min = 4, max = 20, message =  "Логин должен быть не меньше 4 и не больше 20 символов")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Логин должен быть только из букв, цифр и нижних подчеркиваний")
    private String login;

    @NotBlank(message = "Пароль не заполнен")
    @Size(min = 4, max = 20, message = "Пароль должен быть не меньше 4 и не больше 20 символов")
    private String password;

    @Email(message = "Неверный формат email")
    @NotBlank(message = "Email не заполнен")
    private String email;

    private String date;

    public User(String login, String password, String email, String date) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.date = date;
    }
}
