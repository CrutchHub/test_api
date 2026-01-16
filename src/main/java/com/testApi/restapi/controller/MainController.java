package com.testApi.restapi.controller;

import com.testApi.restapi.model.UserGetResponse;
import com.testApi.restapi.model.UserPostResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/api/main")
    public UserGetResponse getMethod(){
        simulateDelay();
        return new UserGetResponse();
    }

    @PostMapping("/api/main")
    public UserPostResponse postMethod(@Valid @RequestBody UserPostResponse request){
        simulateDelay();
        return request;
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
