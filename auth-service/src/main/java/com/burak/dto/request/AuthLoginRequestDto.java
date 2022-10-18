package com.burak.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthLoginRequestDto {
    @NotNull(message = "Kullanıcı adı girilmesi zorunludur")
    @Size(min = 3, max = 16)
    private String userName;
    @NotNull(message = "sifre girilmesi zorunludur")
    private String password;


}
