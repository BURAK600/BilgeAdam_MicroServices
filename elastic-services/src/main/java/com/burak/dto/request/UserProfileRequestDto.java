package com.burak.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@Builder

public class UserProfileRequestDto {
    private Long id;
    private Long authId;
    private String userName;
    private String name;
    private String surName;
    private String email;
    private String phone;
    private String address;
    private String avatar;

    @JsonCreator
    public UserProfileRequestDto(Long id, Long authId, String userName, String name, String surName, String email, String phone, String address, String avatar) {
        this.id = id;
        this.authId = authId;
        this.userName = userName;
        this.name = name;
        this.surName = surName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.avatar = avatar;
    }


}

