package com.burak.config.security;

import com.burak.dto.request.FindByAuthIdRequestDto;
import com.burak.dto.response.FindByAuthIdResponseDto;
import com.burak.manager.IUserServiceManager;
import com.burak.utility.JwtTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenManager jwtTokenManager;
    @Autowired
    IUserServiceManager iUserServiceManager;
    @Autowired
    JwtMyUser jwtMyUser;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        if(authHeader!=null&&authHeader.startsWith("Bearer")){
            String token = authHeader.substring(7);

            Optional<Long> authId = jwtTokenManager.getByIdFromToken(token);

            if(authId.isPresent()){
             FindByAuthIdResponseDto userProfile  = iUserServiceManager.findByAuthid(FindByAuthIdRequestDto.builder()
                     .authId(authId.get()).build()).getBody();



             if(userProfile.getUserId()!=null){
/**
 * JwtMYUSER
 */
                 UserDetails userDetails = jwtMyUser.loadByAuthId(userProfile, authId.get());
                 UsernamePasswordAuthenticationToken authenticationToken =
                         new UsernamePasswordAuthenticationToken( userDetails,
                                 null, userDetails.getAuthorities());

                 SecurityContextHolder.getContext().setAuthentication(authenticationToken);

             }

            }
        }

        filterChain.doFilter(request,response);
    }
}
