package com.burak.controller;

import com.burak.dto.request.AuthLoginRequestDto;
import com.burak.dto.request.AuthRegisterRequestDto;
import com.burak.dto.request.GetAllRolesRequestDto;
import com.burak.dto.response.LoginResponseDto;
import com.burak.dto.response.RegisterResponseDto;
import com.burak.rabbitmq.producer.MessageProducer;
import com.burak.repository.entity.Auth;
import com.burak.repository.entity.AuthRoles;
import com.burak.repository.entity.Authorities;
import com.burak.service.AuthRolesService;
import com.burak.service.AuthService;
import com.burak.service.AuthoritiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.burak.constants.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
public class AuthController {

    private final AuthService authService;
    private final MessageProducer messageProducer;
    private final AuthRolesService authRolesService;
    private final AuthoritiesService authoritiesServices;
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

    @GetMapping("/getall")
    public ResponseEntity<List<Auth>> getAllAuth(){
        return  ResponseEntity.ok(authService.findAll());
    }
    @PostMapping("/sendmessage")
    public ResponseEntity<Void> sendMessage(String message, Long code){
        messageProducer.sendMessage(message, code);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/getrolesbyauthid")
    public ResponseEntity<List<String>> getAllRolesBuAuthId(@RequestBody GetAllRolesRequestDto getAllRolesRequestDto){

        return ResponseEntity.ok(authRolesService.getRolesByAuthId(getAllRolesRequestDto.getAuthId()));

    }
    @GetMapping("/getallroles")
    public ResponseEntity<List<Authorities>> getAllRoles(){

        return ResponseEntity.ok(authoritiesServices.findAll());
    }

    @PostMapping("/saveroles")
    public ResponseEntity<Void> saveRoles(String roleName){
        authoritiesServices.save(Authorities.builder()
                .name(roleName)
                .build());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/saveauthroles")
    public ResponseEntity<Void> saveAuthRoles(Long authId, Long roleId){
        authRolesService.save(AuthRoles.builder()
                .authId(authId)
                .roleId(roleId)
                .build());
        return ResponseEntity.ok().build();
    }




}
