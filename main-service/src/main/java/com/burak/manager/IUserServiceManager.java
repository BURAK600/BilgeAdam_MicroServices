package com.burak.manager;

import com.burak.dto.request.FindByAuthIdRequestDto;
import com.burak.dto.response.FindByAuthIdResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.CacheRequest;
import java.util.Optional;

@FeignClient(name = "auth-service", url = "${myapplication.user-service.feign-client}/userprofile", decode404 = true)
public interface IUserServiceManager {

    @PostMapping("/findbyauthid")
    ResponseEntity<FindByAuthIdResponseDto> findByAuthid(@RequestBody FindByAuthIdRequestDto dto);



}
