package com.burak.service;

import com.burak.dto.request.AuthLoginRequestDto;
import com.burak.dto.request.AuthRegisterRequestDto;
import com.burak.exception.AuthServiceException;
import com.burak.exception.ErrorType;
import com.burak.manager.IUserProfileManager;
import com.burak.rabbitmq.model.CreateProfile;
import com.burak.rabbitmq.producer.CreateProfileProducer;
import com.burak.repository.IAuthRepository;
import com.burak.repository.entity.Auth;
import com.burak.repository.enums.Roles;
import com.burak.utility.JwtTokenManager;
import com.burak.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth, Long> {

    private final IAuthRepository repository;
    private final JwtTokenManager jwtTokenManager;

    private final IUserProfileManager iUserProfileManager;

    private final CreateProfileProducer createProfileProducer;

    public AuthService(IAuthRepository repository, JwtTokenManager jwtTokenManager, IUserProfileManager iUserProfileManager, CreateProfileProducer createProfileProducer) {
        super(repository);
        this.repository = repository;
        this.jwtTokenManager = jwtTokenManager;
        this.iUserProfileManager = iUserProfileManager;
        this.createProfileProducer = createProfileProducer;
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
            try {
                createProfileProducer.createProfile(CreateProfile.builder()
                        .authId(auth.getId())
                        .email(auth.getEmail())
                        .userName(auth.getUserName())
                        .build());
            }catch (Exception e){
                delete(auth);
            }
            return true;
        }
        return false;

    }



    public String dologin(AuthLoginRequestDto authLoginRequestDto) {
        Optional<Auth> auth = repository.findOptionalByUserNameAndPassword(authLoginRequestDto.getUserName(),
                authLoginRequestDto.getPassword());
        if(auth.isEmpty()) throw new AuthServiceException(ErrorType.LOGIN_ERROR_001);
        return jwtTokenManager.createToken(auth.get().getId());

    }


}

