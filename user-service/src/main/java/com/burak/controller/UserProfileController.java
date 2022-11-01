package com.burak.controller;

import com.burak.dto.request.UserProfileSaveRequestDto;
import com.burak.dto.request.UserProfileUpdateRequestDto;
import com.burak.repository.entity.UserProfile;
import com.burak.service.UserProfileService;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.burak.constants.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(USERPROFILE)
public class UserProfileController {

    private final UserProfileService userProfileService;

    /**
     * Kullanıcı kaydı, auth service  te yaplıyor ve burada olan bilgiler userprofile-serivce e gönderiliyor.
     * Auth-Service ten gelecek olan parametreler:
     * 1-userName
     * 2-email
     * 3-authid
     *
     * @return
     */

    @PostMapping(SAVE)
    public ResponseEntity<Boolean> save(@RequestBody UserProfileSaveRequestDto userProfileSaveRequestDto){

        return ResponseEntity.ok(userProfileService.save(userProfileSaveRequestDto));
    }
    @PostMapping(UPDATE)
    public ResponseEntity<Boolean> update(UserProfileUpdateRequestDto userProfileUpdateRequestDto){

        return ResponseEntity.ok(userProfileService.update(userProfileUpdateRequestDto));
    }
    @PostMapping(FIND_BY_ID)
    public ResponseEntity<UserProfile> findById(){
        return null;
    }
    @GetMapping(USERPROFILE_LIST)
    public ResponseEntity<List<UserProfile>> userList(){
        List<UserProfile> list = userProfileService.findAll();
        return ResponseEntity.ok(list);
    }

    // REDIS kullanımı
    @GetMapping("/getupper")
    public ResponseEntity<String> getUpperCase(Long authId) {
        return ResponseEntity.ok(userProfileService.getUpperCase(authId));
    }


    @PostMapping("/savecachable")
    public ResponseEntity<Void> updateUser(@RequestBody UserProfile userProfile){
        userProfileService.updateCacheReset(userProfile);
        return ResponseEntity.ok().build();
    }
@GetMapping("/getallpage")
    public ResponseEntity<Page<UserProfile>> getAllPage(int pageSize, int pageNumber, String parameter, String direction){
        return ResponseEntity.ok(userProfileService.getAllPage(pageSize,pageNumber,parameter,direction));
    }

    @GetMapping("/getallslice")
    public ResponseEntity<Slice<UserProfile>> getAllSlice(int pageSize, int pageNumber, String parameter, String direction){
        return ResponseEntity.ok(userProfileService.getAllPage(pageSize,pageNumber,parameter,direction));
    }

@PostMapping("/saveall")
    public ResponseEntity<Void> saveAll(List<UserProfileSaveRequestDto> dtos){
        dtos.forEach(dto->userProfileService.save(dto));
        return ResponseEntity.ok().build();
    }
}
