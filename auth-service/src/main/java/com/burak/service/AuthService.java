package com.burak.service;

import com.burak.dto.request.AuthLoginRequestDto;
import com.burak.dto.request.AuthRegisterRequestDto;
import com.burak.exception.AuthServiceException;
import com.burak.exception.ErrorType;
import com.burak.repository.IAuthRepository;
import com.burak.repository.entity.Auth;
import com.burak.repository.enums.Roles;
import com.burak.utility.ServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service

public class AuthService extends ServiceManager<Auth, Long> {

    private final IAuthRepository repository;

    public AuthService(IAuthRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Boolean save(AuthRegisterRequestDto authRegisterRequestDto){
        Auth auth = Auth.builder().userName(authRegisterRequestDto.getUserName()).
                password(authRegisterRequestDto.getPassword()).roles(Roles.ROLE_USER)
                .email(authRegisterRequestDto.getEmail())
                .build();

        if(authRegisterRequestDto.getAdmincode() != null){
        if (authRegisterRequestDto.getAdmincode().equals("Admin")){
            auth.setRoles(Roles.ROLE_ADMIN);
        }};
        save(auth);
        if(auth.getId() != null){
            return true;
        }
        return false;

    }

    public String dologin(AuthLoginRequestDto authLoginRequestDto) {
        Optional<Auth> auth = repository.findOptionalByUserNameAndPassword(authLoginRequestDto.getUserName(),
                authLoginRequestDto.getPassword());
        if(auth.isEmpty()) throw new AuthServiceException(ErrorType.LOGIN_ERROR_001);
        return "Token: " + auth.get().getId();

    }
}

