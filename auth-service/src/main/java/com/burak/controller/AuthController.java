package com.burak.controller;

import com.burak.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.burak.constants.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
public class AuthController {

    private final AuthService authService;

    @PostMapping(DOLOGIN)
    public ResponseEntity<Void> dologin(){
        return ResponseEntity.ok().build();
    }

    @PostMapping(REGISTER)
    public ResponseEntity<Void> register(){
        return ResponseEntity.ok().build();
    }






}
