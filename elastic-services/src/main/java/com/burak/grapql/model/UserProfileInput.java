package com.burak.grapql.model;


import lombok.Data;

@Data
public class UserProfileInput {
    Long authId;
    String userName;
    String email;

}
