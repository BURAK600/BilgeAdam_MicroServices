package com.burak.service;

import com.burak.repository.IAuthoritiesRepository;
import com.burak.repository.entity.Authorities;
import com.burak.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class AuthoritiesService extends ServiceManager<Authorities, Long> {

    private final IAuthoritiesRepository iAuthoritiesRepository;

    public AuthoritiesService(IAuthoritiesRepository iAuthoritiesRepository) {
        super(iAuthoritiesRepository);
        this.iAuthoritiesRepository = iAuthoritiesRepository;
    }
}
