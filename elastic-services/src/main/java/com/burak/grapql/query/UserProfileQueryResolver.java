package com.burak.grapql.query;

import com.burak.repository.IUserProfileRepository;
import com.burak.repository.entity.UserProfile;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProfileQueryResolver implements GraphQLQueryResolver {

    private final IUserProfileRepository iUserProfileRepository;


    public Iterable<UserProfile> findByUserName(String userName){
        return iUserProfileRepository.findByUserNameContaining(userName);
    }
    public Iterable<UserProfile> findAll(){
        return iUserProfileRepository.findAll();
    }




}
