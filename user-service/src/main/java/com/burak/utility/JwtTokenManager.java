package com.burak.utility;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.burak.exception.ErrorType;
import com.burak.exception.UserServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtTokenManager {
    @Value("${myjwt.secretkey")
    private String secretKey;
    @Value("${myjwt.audience")
    private String audience;
    @Value("${myjwt.issuer")
    private String issuer;


    public String createToken(Long authId) {
        String token = null;
        try {
            /**
             * Token yayımcısı -> BilgeAdamAuth
             * Token uretim tarihi
             * Token ne kadar bir süre ile geçerli olacak
             *  Token içerisine tekrar kullanabilmek üzere Claim nesneleri konulabilir.
             *  Bu nesneler Key-Value seklinde bilgi tutarlar ve public olarak görüntülenebilirler.
             *
             *  Token bilgisinin sifrelenmesi gereklidir. Bu nedenler imzalama yöntemi secilmeli ve belirlenen
             *  algoritma ile sign islemi yapılmalıdır.
             */

            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            token = JWT.create()
                    .withAudience(audience)//Kitle
                    .withIssuer(issuer)//Yayımcı
                    .withIssuedAt(new Date())//Olusturulma zamanı
                    .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * 60))) // gecersiz kılınma zamanı (60 sn sonra
                    .withClaim("authId", authId) // kullalıcak bilgiler
                    .sign(algorithm); // sifreleme-imzalama islemi yapılırç
            return token;
        } catch (Exception e) {
            return null;

        }}

        public Optional<Long> getByIdFromToken(String token){
            try {
                Algorithm algorithm = Algorithm.HMAC256(secretKey);
                JWTVerifier jwtVerifier = JWT.require(algorithm)
                        .withAudience(audience)
                        .withIssuer(issuer)//Yayımcı
                        .build(); // kod olşuturulur.
                DecodedJWT decodedToken = jwtVerifier.verify(token); // tokenin gecerliligi dogrulanır.
                if(decodedToken==null) throw new UserServiceException(ErrorType.GECERSIZ_TOKEN);
                Long authId = decodedToken.getClaim("authId").asLong();
                return Optional.of(authId);

            }catch (Exception e){
                throw new UserServiceException(ErrorType.GECERSIZ_TOKEN);
            }

        }
    }



