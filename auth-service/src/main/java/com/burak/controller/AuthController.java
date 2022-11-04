package com.burak.controller;

import com.burak.dto.request.AuthLoginRequestDto;
import com.burak.dto.request.AuthRegisterRequestDto;
import com.burak.dto.response.LoginResponseDto;
import com.burak.dto.response.RegisterResponseDto;
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
    public ResponseEntity<LoginResponseDto> dologin(@RequestBody @Valid AuthLoginRequestDto authLoginRequestDto){

        String token = authService.dologin(authLoginRequestDto);

        return ResponseEntity.ok(LoginResponseDto.builder()
                        .token(token)
                        .code(110)
                        .message("Giriş başarılı")
                .build());
    }


    @PostMapping(REGISTER)
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid AuthRegisterRequestDto authRegisterRequestDto){

        if(authService.save(authRegisterRequestDto)){
            return ResponseEntity.ok(RegisterResponseDto.builder()
                            .code(100L)
                            .message("kayıt başarılı")
                    .build());
        }
        return ResponseEntity.badRequest().body(RegisterResponseDto.builder()
                .message("kayıt başarısız")
                        .code(101L)
                .build());
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
