package com.burak.repository.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class Competition {
    @Id
    String id;
    String name;
    String description;
    Long startDate;
    Long endDate;
    String userId;
    String status;

}
