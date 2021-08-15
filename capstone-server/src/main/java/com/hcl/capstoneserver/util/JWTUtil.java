package com.hcl.capstoneserver.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtil {
    /**
     * take resource file secret key
     */
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    /**
     * take resource file JWT valid time
     */
    @Value("${jwt.validity}")
    int JWT_VALIDITY;

    /**
     * Method to extract username
     *
     * @param token jwt token sent by front-end
     * @return username
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Method to extract expire time
     *
     * @param token jwt token sent by front-end
     * @return expire date
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Method to extractUserType
     *
     * @param token jwt token sent by front-end
     * @return user type
     */
    public String extractUserType(String token) {
        return extractClaim(token, (claims -> claims.get("userType"))).toString();
    }

    /**
     * Method to extract data by JWT token
     *
     * @param token          jwt token sent by front-end
     * @param claimsResolver claims type
     * @return type depend on, claims type (sometimes it String(user-id, user-type), Date(expire date))
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Method to extract all data by JWT token
     *
     * @param token jwt token sent by front-end
     * @return Claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * Method to check JWT token is expired or not
     *
     * @param token jwt token sent by front-end
     * @return if it expired then return true, otherwise return false
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Method to generate new JWT token
     *
     * @param userDetails get user details
     * @return new token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("userType", userDetails.getAuthorities().toArray()[0]);
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Method to create JWT token
     *
     * @param claims  get claims map
     * @param subject get subject
     * @return new created token
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    /**
     * Method to check JWT toke is valid or not
     *
     * @param token jwt token sent by front-end
     * @return if token is valid, then return true, otherwise return false
     */
    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}
