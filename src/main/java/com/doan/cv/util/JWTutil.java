package com.doan.cv.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTutil {
    private final String SECRET = "CAI-DJT-CON-DI-ME-MAY-NHO-MAT-BO-MAY-DAY-CON-CHO-DE-MAT-DAY";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    private Claims getTokenBody(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    public String generateToken(String username){
        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*3600))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token){
        return getTokenBody(token).getSubject();
    }

    private boolean isTokenExpired(String token) {
        return getTokenBody(token).getExpiration().before( new Date());
    }

    public boolean validateToken(String username, UserDetails userDetails, String token){
        return username.equals(userDetails.getUsername()) && isTokenExpired(token);
    }


}
