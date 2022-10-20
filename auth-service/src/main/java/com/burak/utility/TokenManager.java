package com.burak.utility;

import org.springframework.stereotype.Component;

@Component
public class TokenManager {

    public String generateToken(Long id){
        Long time = System.currentTimeMillis();

        return "Token:"+time+":"+id;
    }


    public Long  getId(String token){
        String[] data = token.split(":");  // {Token,1666167395622,12

        if(data.length !=3) return null;
        if(!data[0].equals("Token")) return null;

        /**
         * Token içinde zaman parametresi gelir. Buna göre 30sn içinde token geçersiz kılınsın
         */
        Long time = Long.parseLong(data[1]);
        Long now = System.currentTimeMillis();
        Boolean verifTime = (now-time-(1000*30))>0;
        if(verifTime) return null;
        return Long.parseLong(data[2]);


    }


}
