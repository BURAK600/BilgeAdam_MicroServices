package com.burak.manager;


import com.burak.repository.entity.UserProfile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import static com.burak.constants.ApiUrls.USERPROFILE_LIST;

@FeignClient(name = "user-service", url = "${myapplication.user-service.feign-client}", decode404 = true)
public interface IUserProfileManager {

    @GetMapping(USERPROFILE_LIST)
    ResponseEntity<List<UserProfile>> userList();


}
