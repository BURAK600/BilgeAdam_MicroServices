package com.burak.service;

import com.burak.dto.request.UserProfileSaveRequestDto;
import com.burak.dto.request.UserProfileUpdateRequestDto;
import com.burak.exception.ErrorType;
import com.burak.exception.UserServiceException;
import com.burak.repository.IUserProfileRepository;
import com.burak.repository.entity.UserProfile;
import com.burak.utility.JwtTokenManager;
import com.burak.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile, Long> {

    private final IUserProfileRepository iUserProfileRepository;
    private final JwtTokenManager jwtTokenManager;
    public UserProfileService(IUserProfileRepository iUserProfileRepository, JwtTokenManager jwtTokenManager) {
        super(iUserProfileRepository);
        this.iUserProfileRepository = iUserProfileRepository;

        this.jwtTokenManager = jwtTokenManager;
    }

    public Boolean save(UserProfileSaveRequestDto userProfileSaveRequestDto) {
        save(UserProfile.builder().userName(userProfileSaveRequestDto.getUserName()).authId(userProfileSaveRequestDto.getAuthId())
                .email(userProfileSaveRequestDto.getEmail())

                .build());
        return true;
    }

    public Boolean update(UserProfileUpdateRequestDto userProfileUpdateRequestDto){
        Optional<Long> authid =jwtTokenManager.getByIdFromToken(userProfileUpdateRequestDto.getToken());
        if(authid.isEmpty()) throw new UserServiceException(ErrorType.GECERSIZ_ID);

        Optional<UserProfile> profile = iUserProfileRepository.findOptionalByAuthId(authid.get());

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
