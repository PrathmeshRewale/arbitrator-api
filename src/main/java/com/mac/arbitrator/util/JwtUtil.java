package com.mac.arbitrator.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Value("${app.jwt.access-token-expire}")
    private Long accessTokenExpire;

    @Value("${app.jwt.refresh-token-expire}")
    private Long refreshTokenExpire;

    @Value("${app.jwt.access-token-secret}")
    private String accessTokenSecret;

    @Value("${app.jwt.refresh-token-secret}")
    private String refreshTokenSecret;

    private Map<String, Key> getKeys(){
        Key accessSecret = Keys.hmacShaKeyFor(accessTokenSecret.getBytes(StandardCharsets.UTF_8));
        Key refreshSecret = Keys.hmacShaKeyFor(refreshTokenSecret.getBytes(StandardCharsets.UTF_8));

        return Map.of("access",accessSecret,"refresh",refreshSecret);
    }

    //  Access token config

    public String generateAccessToken(String username){
        return Jwts.
                builder().
                setSubject(username)
                .setExpiration(Date.from(Instant.now().plusMillis(accessTokenExpire)))
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(getKeys().get("access"))
                .compact();
    }

    public String extractAccessTokenUserName(String token){
        return extractAccessTokenClaim(token,Claims::getSubject);
    }

    public Date extractAccessTokenExpiration(String token){
        return extractAccessTokenClaim(token,Claims::getExpiration);
    }

    private <T> T extractAccessTokenClaim(String token, Function<Claims, T> resolver){
        Claims claims = extractAllAccessTokenClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllAccessTokenClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKeys().get("access"))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

//    access token validation

    public boolean isAccessTokenValid(String token,String username){
        String extractedUserNameFromAccessToken = extractAccessTokenUserName(token);

        return (extractedUserNameFromAccessToken.equals(username) && !isAccessTokenExpired(token));
    }

    public boolean isAccessTokenExpired(String token){
        return extractAccessTokenExpiration(token).before(new Date());
    }

//    refresh token config

    public String generateRefreshToken(String username){
        return Jwts.
                builder().
                setSubject(username)
                .setExpiration(Date.from(Instant.now().plusMillis(refreshTokenExpire)))
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(getKeys().get("refresh"))
                .compact();
    }

    public String extractRefreshTokenUserName(String token){
        return extractRefreshTokenClaim(token,Claims::getSubject);
    }

    private Date extractRefreshTokenExpiration(String token){
        return extractRefreshTokenClaim(token,Claims::getExpiration);
    }

    private <T> T extractRefreshTokenClaim(String token, Function<Claims, T> resolver){
        Claims claims = extractAllRefreshTokenClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllRefreshTokenClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKeys().get("refresh"))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

//    validate refresh token

    public boolean isRefreshTokenValid(String token,String username){
        String extractedUserNameFromRefreshToken = extractRefreshTokenUserName(token);
        return (extractedUserNameFromRefreshToken.equals(username) && !isRefreshTokenExpired(token));
    }

    private boolean isRefreshTokenExpired(String token){
        return extractRefreshTokenExpiration(token).before(new Date());
    }
}
