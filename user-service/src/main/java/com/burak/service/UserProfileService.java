package com.burak.service;

import com.burak.dto.request.FindByAuthIdRequestDto;
import com.burak.dto.request.GetMyProfileRequestDto;
import com.burak.dto.request.UserProfileSaveRequestDto;
import com.burak.dto.request.UserProfileUpdateRequestDto;
import com.burak.exception.ErrorType;
import com.burak.exception.UserServiceException;
import com.burak.manager.IElasticSearchManager;
import com.burak.repository.IUserProfileRepository;
import com.burak.repository.entity.UserProfile;
import com.burak.utility.JwtTokenManager;
import com.burak.utility.ServiceManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile,String> {

    private final IUserProfileRepository iUserProfileRepository;
    private final JwtTokenManager jwtTokenManager;

    private final CacheManager cacheManager;

    private final IElasticSearchManager iElasticSearchManager;
    public UserProfileService(IUserProfileRepository iUserProfileRepository, JwtTokenManager jwtTokenManager, CacheManager cacheManager, IElasticSearchManager iElasticSearchManager) {
        super(iUserProfileRepository);
        this.iUserProfileRepository = iUserProfileRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.cacheManager = cacheManager;
        this.iElasticSearchManager = iElasticSearchManager;
    }

    public Optional<UserProfile> findByAuthId(Long authId){
        return iUserProfileRepository.findOptionalByAuthId(authId);
    }

    public Boolean save(UserProfileSaveRequestDto userProfileSaveRequestDto) {
       UserProfile userProfile =  save(UserProfile.builder().userName(userProfileSaveRequestDto.getUserName()).authId(userProfileSaveRequestDto.getAuthId())
                .email(userProfileSaveRequestDto.getEmail())

                .build());
//       iElasticSearchManager.save(userProfile);
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
//        iElasticSearchManager.update(userProfile);
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
         * Bu i??lem ilgili method taraf??ndan tutulan t??m ??nbelleklenmi?? datay?? temizler
         */

//        cacheManager.getCache("uppercase").clear();

        /**
         * Bu i??lem ilgili method taraf??ndan tutulan datadan ilgili getAuthId 'nin cache bilgisini siler.
         */

        cacheManager.getCache("uppercase").evict(userProfile.getAuthId());
    }
    /**
     * 500 adet kay??t var
     * - 10 'ar adet kay??t g??stermek istedi??imde 50 adet sayfa olu??ur.
     * 2. istedi??imde 21-30 kay??tlar g??sterilir.
     *
     * @param pageSize -> her seferinde ka?? kay??t d??necegini belirler
     * @param currentPageNumber -> ge??erli sayfan??n?? hangisi oldu??unu belirler.
     * @param sortParameter -> s??ralama isleminin hangi kolona g??re yap??lacag??n?? belirler
     * @param sortDirection -> s??ralama y??n?? asc, desc
     *
     *
     */

    public Page<UserProfile> getAllPage(int pageSize, int currentPageNumber, String sortParameter, String sortDirection){

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortParameter);
        Pageable pageable = PageRequest.of(currentPageNumber,pageSize, sort );
        return iUserProfileRepository.findAll(pageable);
    }

    public Page<UserProfile> getAllSlice(int pageSize, int currentPageNumber, String sortParameter, String sortDirection){

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortParameter);
        Pageable pageable = PageRequest.of(currentPageNumber,pageSize, sort );
        return iUserProfileRepository.findAll(pageable);
    }

    public UserProfile findByToken(GetMyProfileRequestDto getMyProfileRequestDto){
        Optional<Long> authId = jwtTokenManager.getByIdFromToken(getMyProfileRequestDto.getToken());
        if(authId.isEmpty()) throw new UserServiceException(ErrorType.GECERSIZ_ID);
        Optional<UserProfile> userProfile = iUserProfileRepository.findOptionalByAuthId(authId.get());
        if(userProfile.isEmpty()) throw new UserServiceException(ErrorType.KULLANICI_BULUNAMADI);
            return userProfile.get();
        }

    public UserProfile findByAuthid(FindByAuthIdRequestDto dto) {
        return iUserProfileRepository.findOptionalByAuthId(dto.getAuthId()).get();
    }
}




