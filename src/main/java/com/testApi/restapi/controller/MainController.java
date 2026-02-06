package com.testApi.restapi.controller;

import com.testApi.restapi.model.DataBaseWorker;
import com.testApi.restapi.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

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
        User user = dataBaseWorker.selectUserByLogin(login);
        if (user == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("Пользователь не найден");
        }
        return ResponseEntity.ok(user);
    }


    @PostMapping("/api/main")
    public ResponseEntity<?> postMethod(@Valid @RequestBody User request){
        simulateDelay();
        request.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        try{
            int rowsAffected = dataBaseWorker.insertUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Создано строк: " + rowsAffected);
        }
        catch(DuplicateKeyException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Запись с подобными данными уже есть");
        }
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
