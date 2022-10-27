package com.burak.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
/**
 * NoSQL olan elasticsearch tablo yerine document mantığı ile çalışır.
 */
@Document(indexName = "userprofile")
public class UserProfile {
    @Id
    private String id;
    private Long userId;
    private Long authId;
    private String userName;
    private String name;
    private String surName;
    private String email;
    private String phone;
    private String address;
    private String avatar;


}
