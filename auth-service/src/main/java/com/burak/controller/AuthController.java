package com.burak.controller;

import com.burak.dto.request.AuthLoginRequestDto;
import com.burak.dto.request.AuthRegisterRequestDto;
import com.burak.repository.entity.Auth;
import com.burak.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.burak.constants.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
public class AuthController {

    private final AuthService authService;

    @PostMapping(DOLOGIN)
    public ResponseEntity<String> dologin(@RequestBody @Valid AuthLoginRequestDto authLoginRequestDto){

        return ResponseEntity.ok(authService.dologin(authLoginRequestDto));
    }


    @PostMapping(REGISTER)
    public ResponseEntity<Void> register(@RequestBody @Valid AuthRegisterRequestDto authRegisterRequestDto){

        if(authService.save(authRegisterRequestDto)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }


}
