package com.testApi.restapi.controller;

import com.testApi.restapi.model.DataBaseWorker;
import com.testApi.restapi.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class MainController {

    private final DataBaseWorker dataBaseWorker;

    public MainController() {
        String dbUrl = System.getenv("SPRING_DATASOURCE_URL");
        String dbUser = System.getenv("SPRING_DATASOURCE_USERNAME");
        String dbPass = System.getenv("SPRING_DATASOURCE_PASSWORD");
        this.dataBaseWorker = new DataBaseWorker(dbUrl, dbUser, dbPass);
    }
    @GetMapping("/api/main")
    public ResponseEntity<?> getMethod(@RequestParam String login) {
        simulateDelay();
        try{
            User user = dataBaseWorker.selectUserByLogin(login);
            if (user == null){
                throw new SQLException("Пользователь " + login + " не найден");
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
//                        body("Пользователь не найден");
            }
            return ResponseEntity.ok(user);
        }
        catch (SQLException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                      body("Пользователь не найден");
        }

    }


    @PostMapping("/api/main")
    public ResponseEntity<?> postMethod(@Valid @RequestBody User request){
        simulateDelay();
        request.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        int rowsAffected = dataBaseWorker.insertUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Создано строк: " + rowsAffected);
    }

    private void simulateDelay(){
        try {
            int delay = 1000 + (int)(Math.random() * 1000);
            Thread.sleep(delay);
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
