package com.burak.service;

import com.burak.repository.IAuthRepository;
import com.burak.repository.entity.Auth;
import com.burak.utility.ServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service

public class AuthService extends ServiceManager<Auth, Long> {

    private final IAuthRepository repository;

    public AuthService(IAuthRepository repository) {
        super(repository);
        this.repository = repository;
    }
}

