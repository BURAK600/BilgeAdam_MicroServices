package com.burak.service;

import com.burak.dto.request.UserProfileSaveRequestDto;
import com.burak.dto.request.UserProfileUpdateRequestDto;
import com.burak.exception.ErrorType;
import com.burak.exception.UserServiceException;
import com.burak.repository.IUserProfileRepository;
import com.burak.repository.entity.UserProfile;
import com.burak.utility.JwtTokenManager;
import com.burak.utility.ServiceManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile, Long> {

    private final IUserProfileRepository iUserProfileRepository;
    private final JwtTokenManager jwtTokenManager;

    private final CacheManager cacheManager;
    public UserProfileService(IUserProfileRepository iUserProfileRepository, JwtTokenManager jwtTokenManager, CacheManager cacheManager) {
        super(iUserProfileRepository);
        this.iUserProfileRepository = iUserProfileRepository;

        this.jwtTokenManager = jwtTokenManager;
        this.cacheManager = cacheManager;
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

@Cacheable(value = "uppercase")
    public String getUpperCase(Long autId){


        try{
            Thread.sleep(3000);

        }catch (Exception e){

        }

        Optional<UserProfile> userProfile = iUserProfileRepository.findOptionalByAuthId(autId);
        if(userProfile.isEmpty()) return "";
        return userProfile.get().getName().toUpperCase();
    }


    public void updateCacheReset(UserProfile userProfile) {
        save(userProfile);
        /**
         * Bu işlem ilgili method tarafından tutulan tüm önbelleklenmiş datayı temizler
         */

//        cacheManager.getCache("uppercase").clear();

        /**
         * Bu işlem ilgili method tarafından tutulan datadan ilgili getAuthId 'nin cache bilgisini siler.
         */

        cacheManager.getCache("uppercase").evict(userProfile.getAuthId());
    }
}
