package com.burak.service;

import com.burak.dto.request.UserProfileSaveRequestDto;
import com.burak.dto.request.UserProfileUpdateRequestDto;
import com.burak.exception.ErrorType;
import com.burak.exception.UserServiceException;
import com.burak.repository.IUserProfileRepository;
import com.burak.repository.entity.UserProfile;
import com.burak.utility.ServiceManager;
import com.burak.utility.TokenManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile, Long> {

    private final IUserProfileRepository iUserProfileRepository;
    private final TokenManager tokenManager;
    public UserProfileService(IUserProfileRepository iUserProfileRepository, TokenManager tokenManager) {
        super(iUserProfileRepository);
        this.iUserProfileRepository = iUserProfileRepository;
        this.tokenManager = tokenManager;
    }

    public Boolean save(UserProfileSaveRequestDto userProfileSaveRequestDto) {
        save(UserProfile.builder().userName(userProfileSaveRequestDto.getUserName()).authId(userProfileSaveRequestDto.getAuthId())
                .email(userProfileSaveRequestDto.getEmail())

                .build());
        return true;
    }

    public Boolean update(UserProfileUpdateRequestDto userProfileUpdateRequestDto){
        Long authid =tokenManager.getId(userProfileUpdateRequestDto.getToken());
        if(authid == null) throw new UserServiceException(ErrorType.GECERSIZ_TOKEN);

        Optional<UserProfile> profile = iUserProfileRepository.findOptionalByAuthId(authid);

        if(profile.isEmpty()) throw new UserServiceException(ErrorType.KULLANICI_BULUNAMADI);

        UserProfile userProfile = profile.get();

        userProfile.setAddress(userProfileUpdateRequestDto.getAddress());
        userProfile.setPhone(userProfileUpdateRequestDto.getPhone());
        userProfile.setAvatar(userProfileUpdateRequestDto.getAvatar());
        userProfile.setName(userProfileUpdateRequestDto.getName());
        userProfile.setSurName(userProfileUpdateRequestDto.getSurName());
        save(userProfile);
        return true;

    }
}
