package com.mailman.auth_service.Util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "secret_key";
    //generate JWT token
    public String generateToken(String userName) {
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();
    }

    //Validating the JWT token
    public Boolean validateToken(String token, String userName) {
        String userDetails = extractUserName(token);
        return userDetails.equals(userName) && !isTokenExpiration(token);
    }

    private boolean isTokenExpiration(String token) {
        return extractExpiration(token).before(new Date());
    }

    //Extract username from JWT
    public String extractUserName(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJwt(token)
                .getBody()
                .getSubject();
    }

    //check if token is expired or not
    public Date extractExpiration(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJwt(token)
                .getBody()
                .getExpiration();
    }
    //extracting the expirationdate from the token

}