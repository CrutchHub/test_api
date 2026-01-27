package com.testApi.restapi.controller;

import com.testApi.restapi.model.UserGetResponse;
import com.testApi.restapi.model.UserPostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private static final ResponseEntity<UserGetResponse> GET_RESPONSE = ResponseEntity.ok(UserGetResponse.
            getInstance());

    @GetMapping("/api/main")
    public ResponseEntity<UserGetResponse> getMethod() {
//        simulateDelay();
        return GET_RESPONSE;
    }


    @PostMapping("/api/main")
    public ResponseEntity<UserPostResponse> postMethod(@Valid @RequestBody UserPostResponse request){
//        simulateDelay();
        return ResponseEntity.status(HttpStatus.CREATED).body(request);
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
