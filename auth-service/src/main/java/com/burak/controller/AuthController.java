package com.burak.controller;

import com.burak.dto.request.AuthLoginRequestDto;
import com.burak.dto.request.AuthRegisterRequestDto;
import com.burak.rabbitmq.producer.MessageProducer;
import com.burak.repository.entity.Auth;
import com.burak.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.burak.constants.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
public class AuthController {

    private final AuthService authService;
    private final MessageProducer messageProducer;

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

    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hello!!");

    }
    @PostMapping("/sendmessage")
    public ResponseEntity<Void> sendMessage(String message, Long code){
        messageProducer.sendMessage(message, code);
        return ResponseEntity.ok().build();
    }




}
