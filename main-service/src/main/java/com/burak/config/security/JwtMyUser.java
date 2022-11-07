package com.burak.config.security;


import com.burak.dto.request.GetAllRolesRequestDto;
import com.burak.dto.response.FindByAuthIdResponseDto;
import com.burak.manager.AuthServiceManager;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class JwtMyUser implements UserDetailsService {

    private final AuthServiceManager authServiceManager;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails loadByAuthId(FindByAuthIdResponseDto profile, Long authId){

        List<String> roles = authServiceManager.getAllRolesBuAuthId(

                GetAllRolesRequestDto.builder()
                        .authId(authId)
                        .build()
        ).getBody();


        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        roles.forEach(role->{


        grantedAuthorities.add(new SimpleGrantedAuthority(role));
        });
        return User.builder()
                .username(profile.getUserName())
                .password("")
                .accountExpired(false)
                .accountLocked(false)
                .authorities(grantedAuthorities)
                .build();
    }
}

