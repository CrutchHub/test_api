package com.testApi.restapi.controller;

import com.testApi.restapi.model.DataBaseWorker;
import com.testApi.restapi.model.FileWorker;
import com.testApi.restapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
public class MainController {

    private final DataBaseWorker dataBaseWorker;
    private final Random random = new Random();
    private final FileWorker fileWorker;

    @Autowired
    public MainController(DataBaseWorker dataBaseWorker, FileWorker fileWorker) {
        this.dataBaseWorker = dataBaseWorker;
        this.fileWorker = fileWorker;
    }

    @GetMapping("/api/main")
    public ResponseEntity<?> getMethod(@RequestParam String login) {
//        simulateDelay();
        try{
            User user = dataBaseWorker.selectUserByLogin(login);
            fileWorker.writeUserToFile(user);
            return ResponseEntity.ok(user);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("Ошибка поиска пользователя: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("Ошибка записи пользователя в файл");
        }
    }

    @GetMapping("/api/file")
    public ResponseEntity<?> getRandomUserFromFile(){
        try {
            Map<String, String> userData = fileWorker.getRandomUserFromFile();
            return ResponseEntity.ok(userData);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка чтения файла");
        }
    }


    @PostMapping("/api/main")
    public ResponseEntity<?> postMethod(@Valid @RequestBody User request){
        simulateDelay();
        request.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        int rowsAffected = dataBaseWorker.insertUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Создано строк: " + rowsAffected);
    }

    private synchronized void writeUserToFile(User user) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        String filePathForWrite = "C:\\Users\\user\\Desktop\\filesfortest\\jsonfwithdatafromdb.txt";

        String userJSON = objectMapper.writeValueAsString(user);

        Files.writeString(Paths.get(filePathForWrite), userJSON + System.lineSeparator(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
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
