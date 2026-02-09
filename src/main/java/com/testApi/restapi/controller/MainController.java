package com.testApi.restapi.controller;

import com.testApi.restapi.model.DataBaseWorker;
import com.testApi.restapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public MainController(DataBaseWorker dataBaseWorker) {
        this.dataBaseWorker = dataBaseWorker;
    }

    @GetMapping("/api/main")
    public ResponseEntity<?> getMethod(@RequestParam String login) {
        simulateDelay();
        try{
            User user = dataBaseWorker.selectUserByLogin(login);
            return ResponseEntity.ok(user);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("Ошибка поиска пользователя: " + e.getMessage());
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
