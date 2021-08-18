package com.hcl.capstoneserver.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@SpringBootTest
public class JWTUtilTest {
    @Autowired
    private JWTUtil jwtUtil;

    private String _generateToken() {
        User user = new User(
                "user1",
                "password",
                Collections.singleton(new SimpleGrantedAuthority("USER"))
        );
        return jwtUtil.generateToken(user);
    }

    @Test
    @DisplayName("it should generate a valid jwt token")
    void generateValidToken() {
        Assertions.assertTrue(jwtUtil.validateToken(_generateToken()));
    }

    @Test
    @DisplayName("it should generate a jwt with valid username")
    void generateValidTokenUsername() {
        Assertions.assertEquals("user1", jwtUtil.extractUsername(_generateToken()));
    }

    @Test
    @DisplayName("it should generate jwt with valid user type")
    void generateValidTokenUserType() {
        Assertions.assertEquals("{authority=USER}", jwtUtil.extractUserType(_generateToken()));
    }

    @Test
    @DisplayName("it should throw MalformedJwtException on incorrect jwt tokens")
    void validateInvalidToken() {
        Assertions.assertThrows(MalformedJwtException.class, () -> jwtUtil.validateToken("faketokem"));
    }

    @Test
    @DisplayName("it should throw SignatureException on false jwt tokens")
    void validateMaliciousToken() {
        String maliciousToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmb28iLCJ1c2VyVHlwZSI6eyJhdXRob3JpdHkiOiJBRE1JTiJ9LCJleHAiOjE2MTk2NjE3NDYsImlhdCI6MTYxOTYxODU0Nn0.0U3W34dIZMMK4qqklFzAQn4efVZk12GfW9hOtmDnGdE";
        Assertions.assertThrows(SignatureException.class, () -> jwtUtil.validateToken(maliciousToken));
    }

    @Test
    @DisplayName("it should throw ExpiredJwtException  on expired tokens")
    void validateExpiredTokens() {
        String expiredToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmb28iLCJ1c2VyVHlwZSI6eyJhdXRob3JpdHkiOiJVU0VSIn0sImV4cCI6MTYxOTY2MTc0NiwiaWF0IjoxNjE5NjE4NTQ2fQ.t49tdSoMl-VEQmIUUjiA6oggosL1HJlEEhcsWKlqKKg";
        Assertions.assertThrows(ExpiredJwtException.class, () -> jwtUtil.validateToken(expiredToken));
    }
}
