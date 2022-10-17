package com.burak.repository.entity;

import com.burak.repository.enums.Activated;
import com.burak.repository.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "tblauth")
@Entity

public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    private String email;
    private String phone;
    @Enumerated(EnumType.STRING)
    Activated activated;
    @Enumerated(EnumType.STRING)
    Roles roles;

}
