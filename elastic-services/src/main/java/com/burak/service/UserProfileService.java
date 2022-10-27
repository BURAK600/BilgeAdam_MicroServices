package com.burak.service;

import com.burak.dto.request.UserProfileRequestDto;
import com.burak.grapql.model.UserProfileInput;
import com.burak.repository.IUserProfileRepository;
import com.burak.repository.entity.UserProfile;
import com.burak.utility.ServiceManager;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService extends ServiceManager<UserProfile, Long> {

    private final IUserProfileRepository repository;
    public UserProfileService(IUserProfileRepository repository) {
        super(repository);
        this.repository = repository;

    }

    public Boolean saveInput(UserProfileInput userProfileInput){
        save(UserProfile.builder()
                .authId(userProfileInput.getAuthId())
                .userName(userProfileInput.getUserName())
                        .email(userProfileInput.getEmail())
                        .build()
                );
        return true;
    }

    public void save(UserProfileRequestDto userProfileRequestDto){
        save(UserProfile.builder()
                .name(userProfileRequestDto.getName())
                .surName(userProfileRequestDto.getSurName())
                .phone(userProfileRequestDto.getPhone())
                .address(userProfileRequestDto.getAddress())
                .avatar(userProfileRequestDto.getAvatar())
                .authId(userProfileRequestDto.getAuthId())
                .userId(userProfileRequestDto.getId())
                .email(userProfileRequestDto.getEmail())
                .build());
    }
}
