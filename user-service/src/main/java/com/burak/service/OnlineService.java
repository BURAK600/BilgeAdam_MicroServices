package com.burak.service;

import com.burak.dto.request.GetMyProfileRequestDto;
import com.burak.repository.IOnlineRepository;
import com.burak.repository.entity.Online;
import com.burak.repository.entity.UserProfile;
import com.burak.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OnlineService extends ServiceManager<Online, String> {

    private final IOnlineRepository iOnlineRepository;
    private final UserProfileService userProfileService;


    public OnlineService(IOnlineRepository iOnlineRepository, UserProfileService userProfileService) {
        super(iOnlineRepository);
        this.iOnlineRepository = iOnlineRepository;
        this.userProfileService = userProfileService;
    }

    /**
     * token ile birlikte usereProfileServisten ilgili kişiye ait profil bilgisi çekilir
     * sonra bu bilgiler ile online olma işlemleri yapılır.
     * @param token
     */
    public void doOnline(String token){
        UserProfile userProfile = userProfileService.findByToken(GetMyProfileRequestDto.builder()
                        .token(token)
                .build()
        );
        /**
         * Kişinin daha önceden online/offline bilgisinin kayıtlı olup olmadığına bakılır.
         */
       Optional<Online> online = iOnlineRepository.findOptionalByUserProfileId(userProfile.getId());
       if(online.isEmpty()){
           save(Online.builder()
                   .online(true)
                   .userProfileId(userProfile.getId())
                   .build());
       }
    }

    public void doOffline(String token){
        UserProfile userProfile = userProfileService.findByToken(GetMyProfileRequestDto.builder()
                .token(token)
                .build()
        );
        /**
         * Kişinin daha önceden online/offline bilgisinin kayıtlı olup olmadığına bakılır.
         */
        Optional<Online> online = iOnlineRepository.findOptionalByUserProfileId(userProfile.getId());
        if(online.isEmpty()){
            save(Online.builder()
                            .online(false)
                    .userProfileId(userProfile.getId())
                            .build());
        }else{
            online.get().setOnline(false);
            save(online.get());
        }
    }

    public List<Online> getAllOnList(){
        return iOnlineRepository.findByOnlineTrue();
    }
}
