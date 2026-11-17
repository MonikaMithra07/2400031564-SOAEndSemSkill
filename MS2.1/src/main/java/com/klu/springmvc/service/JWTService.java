package com.klu.springmvc.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

    private final String secretKey = "zsxtfgyhuijmko,lzexrctvgybhunijmkzwexrctyuiowesxfcvgbhjkmnmbhtrdgjhk123456";

    public Map<String, String> validateJWT(String token) throws Exception {
        SecretKey skey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        Claims claim = Jwts.parserBuilder()
                .setSigningKey(skey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        if (claim == null || (claim.getExpiration() != null && claim.getExpiration().before(new Date()))) {
            throw new Exception("Token Invalid!");
        }

        Map<String, String> parsedJWT = new HashMap<>();
        parsedJWT.put("username", claim.getSubject() != null ? claim.getSubject() : String.valueOf(claim.get("un")));
        parsedJWT.put("role", String.valueOf(claim.get("role")));
        parsedJWT.put("id", String.valueOf(claim.get("id")));

        return parsedJWT;
    }
}