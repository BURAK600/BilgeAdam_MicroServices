package com.burak.service;

import com.burak.repository.IAuthRolesRepository;
import com.burak.repository.entity.AuthRoles;
import com.burak.repository.entity.Authorities;
import com.burak.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthRolesService extends ServiceManager<AuthRoles, Long> {
    private final IAuthRolesRepository iAuthRolesRepository;
    private final AuthoritiesService authoritiesService;

    public AuthRolesService(IAuthRolesRepository iAuthRolesRepository, AuthoritiesService authoritiesService) {
        super(iAuthRolesRepository);
        this.iAuthRolesRepository = iAuthRolesRepository;
        this.authoritiesService = authoritiesService;
    }

    public List<String> getRolesByAuthId(Long authId){
        /**
         * öncelikle authid ye ait tüm rol listesini çekiyoruz.
         */
        List<AuthRoles> authRoles = iAuthRolesRepository.findAllByAuthId(authId);
        /**
         * çekilen listedeki roles id leri ile Authorities tabosundan ilgili rolleri çekiyoruz.
         */
        List<String> roles = new ArrayList<>();
        authRoles.forEach(r->{
            Authorities authorities = authoritiesService.findById(r.getRoleId());
            if(authorities!=null)
                roles.add(authorities.getName());
        });
        return roles;
    }

}
