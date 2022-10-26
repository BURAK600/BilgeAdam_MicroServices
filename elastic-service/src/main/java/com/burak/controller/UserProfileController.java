package com.burak.controller;


import com.burak.repository.entity.UserProfile;
import com.burak.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.burak.constants.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ELASTIC)
public class UserProfileController {

    private final UserProfileService userProfileService;


    @GetMapping(GETALL)
    public ResponseEntity<Iterable<UserProfile>> getAll(){

        return ResponseEntity.ok(userProfileService.findAll());
    }
}
