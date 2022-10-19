package com.burak.service;

import com.burak.dto.request.AuthLoginRequestDto;
import com.burak.dto.request.AuthRegisterRequestDto;
import com.burak.dto.request.UserProfileSaveRequestDto;
import com.burak.exception.AuthServiceException;
import com.burak.exception.ErrorType;
import com.burak.manager.IUserProfileManager;
import com.burak.repository.IAuthRepository;
import com.burak.repository.entity.Auth;
import com.burak.repository.enums.Roles;
import com.burak.utility.ServiceManager;
import com.burak.utility.TokenManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth, Long> {

    private final IAuthRepository repository;
    private final TokenManager tokenManager;

    private final IUserProfileManager iUserProfileManager;

    public AuthService(IAuthRepository repository, TokenManager tokenManager, IUserProfileManager iUserProfileManager) {
        super(repository);
        this.repository = repository;
        this.tokenManager = tokenManager;
        this.iUserProfileManager = iUserProfileManager;
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
            iUserProfileManager.save(UserProfileSaveRequestDto.builder()
                            .authId(auth.getId())
                            .email(auth.getEmail())
                            .userName(auth.getUserName())
                    .build());
            return true;
        }
        return false;

    }

    public String dologin(AuthLoginRequestDto authLoginRequestDto) {
        Optional<Auth> auth = repository.findOptionalByUserNameAndPassword(authLoginRequestDto.getUserName(),
                authLoginRequestDto.getPassword());
        if(auth.isEmpty()) throw new AuthServiceException(ErrorType.LOGIN_ERROR_001);
        return tokenManager.generateToken(auth.get().getId());

    }
}

