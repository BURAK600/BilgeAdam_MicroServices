package com.burak.manager;


import com.burak.dto.request.UserProfileSaveRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.burak.constants.ApiUrls.SAVE;


/**
 * DIKKAT
 * name-> benzersiz bir isim olmalıdır. diger türü hata alacaktır.
 */

@FeignClient(name = "user-profile-service", url = "http://localhost:9094/api/v1/userprofile", decode404 =true)
public interface IUserProfileManager {

    @PostMapping(SAVE)
    public ResponseEntity<Boolean> save(@RequestBody UserProfileSaveRequestDto userProfileSaveRequestDto);


}
