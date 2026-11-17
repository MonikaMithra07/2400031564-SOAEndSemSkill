package com.klu.springmvc.service;

import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

    // 256-bit / 32-byte secret key (matches MS1)
	private final String secretKey = "zsxtfgyhuijmko,lzexrctvgybhunijmkzwexrctyuiowesxfcvgbhjkmnmbhtrdgjhk123456";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public Map<String, String> validateJWT(String token) {
        Map<String, String> data = new HashMap<>();
        try {
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            data.put("username", claims.getSubject());
            data.put("id", String.valueOf(claims.get("id")));
            data.put("role", String.valueOf(claims.get("role")));
        } catch (Exception e) {
            throw new RuntimeException("Invalid or expired JWT token");
        }
        return data;
    }
}