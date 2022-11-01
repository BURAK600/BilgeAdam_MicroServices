package com.burak.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthRegisterRequestDto {
   @NotNull(message = "Kullanici adi girilmesi zorunludur.")
   @Size(min = 3, max = 16)
   private String  userName;

   @NotNull(message = "Sifre girilmesi zorunludur.")
   @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
           message = "Şifre en az 8 karakterden oluşmalıdır. En az bir büyük harf, bir küçük harf, bir sayı ve bir özel karakter içermelidir.")
   @Size(min = 8, max = 64)

   private String password;


   @Email(message = "Gecerli bir email adresi girilmelidir.")
   private String email;
   private String admincode;
}
