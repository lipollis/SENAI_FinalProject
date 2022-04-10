package com.apengenharia.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    // VARIÁVEL PARA TRAZER O VALOR QUE FOI DADO NO APPLICATION.PROPERTIES
    // PARA O TEMPO DE EXPIRAÇÃO DO TOKEN
    @Value("${jwt.expiration}")
    private Long expiration;

    // VARIÁVEL PARA TRAZER O VALOR QUE FOI DADO NO APPLICATION.PROPERTIES
    // PARA A PALAVRA SECRETA DO TOKEN
    @Value("${jwt.secret}")
    private String secret;

    // GERA O TOKEN CONFORME O JWT SECRET EMPREGADO EM APPLICATION.PROPERTIES
    // O COMPACT DEIXA A APLICAÇÃO MAIS PERFORMÁTICA COMPACTANDO A CHAVE
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }

    public boolean tokenValido(String token) {
        Claims claims = getClaims(token);
        if(claims != null) {
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());

            if(username != null && expirationDate != null && now.before(expirationDate)) {
                return true;
            }
        }
        return false;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        if(claims != null) {
            return claims.getSubject();
        }
        return null;
    }
}
