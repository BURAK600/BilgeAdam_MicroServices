package com.burak.utility;

import com.burak.controller.UserProfileController;
import com.burak.manager.IUserProfileManager;
import com.burak.repository.entity.UserProfile;
import com.burak.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserComponent {

    private final IUserProfileManager iUserProfileManager;
    private final UserProfileService userProfileService;

    /**
     * postconstruct program ayağa kalktığında çalışışr.
     */
    @PostConstruct
    public void firstRun(){
        List<UserProfile> userProfiles = iUserProfileManager.userList().getBody();
        userProfiles.forEach(userProfile -> {
            userProfile.setId(null);
            userProfile.setUserId(Long.getLong(userProfile.getId()));

            userProfileService.save(userProfile);

        });

    }
}
