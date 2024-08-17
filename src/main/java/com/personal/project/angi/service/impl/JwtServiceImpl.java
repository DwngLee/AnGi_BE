package com.personal.project.angi.service.impl;

import com.personal.project.angi.configuration.security.UserDetailsImpl;
import com.personal.project.angi.constant.TokenConstant;
import com.personal.project.angi.model.dto.response.JwtResponse;
import com.personal.project.angi.service.JwtService;
import com.personal.project.angi.util.Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.signerKey}")
    private String jwtSecret;
    @Value("${jwt.access-expiration}")
    private int accessTokenExpirationMs;
    @Value("${jwt.refresh-expiration}")
    private int refreshTokenExpirationMs;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public JwtResponse generateToken(Authentication authentication) {
        String accessToken = null;
        String refreshToken = null;
        try {
            UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
            accessToken = Jwts.builder()
                    .subject(user.getUserId())
                    .issuer("AnGi")
                    .issuedAt(new Date())
                    .expiration(new Date(new Date().getTime() + accessTokenExpirationMs))
                    .claim("scope", buildScope(user))
                    .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                    .compact();
            refreshToken = UUID.randomUUID().toString();

            saveTokenToRedis(user.getUserId(), refreshToken, TokenConstant.REFRESH_TOKEN, refreshTokenExpirationMs);
        } catch (Exception e) {
            log.error("Generate token fail by error: ", e.getMessage());
            throw e;
        }

        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public String getUserIdFromRefreshToken(String refreshToken) {
        return Objects.requireNonNull(redisTemplate.opsForValue().get(Util.generateRedisKey(TokenConstant.REFRESH_TOKEN, refreshToken))).toString();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes())).build().parseSignedClaims(token);
            // Get user access token from redis and validate
            if (!isTokenInDB(token, TokenConstant.ACCESS_TOKEN)) {
                return true;
            }
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token by: ", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token by: ", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty by: ", ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean validateRefreshToken(String refreshToken) {
        return isTokenInDB(refreshToken, TokenConstant.REFRESH_TOKEN);
    }

    private void saveTokenToRedis(String userId, String token, String type, long expiration) {
        try {
            String key = Util.generateRedisKey(type, token);
            redisTemplate.opsForValue().set(
                    key,
                    userId,
                    expiration,
                    TimeUnit.MILLISECONDS
            );
        } catch (Exception e) {
            log.error("Save token to redis fail by error: ", e.getMessage());
        }
    }


    @Override
    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes())).build().parseSignedClaims(token).getBody();
        return claims.getSubject();
    }

    @Override
    public void revokeRefreshToken(String refreshToken) {
        redisTemplate.delete(Util.generateRedisKey(TokenConstant.REFRESH_TOKEN, refreshToken));
    }

    @Override
    public void revokeAccessToken(String accessToken) {
        Claims claims = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes())).build().parseSignedClaims(accessToken).getBody();
        long expirationRemain = claims.getExpiration().getTime() - System.currentTimeMillis();
        String userId = claims.getSubject();

        saveTokenToRedis(userId, accessToken, TokenConstant.ACCESS_TOKEN, expirationRemain);
    }

    private String buildScope(UserDetailsImpl userDetails) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(userDetails.getAuthorities())) {
            userDetails.getAuthorities().forEach(authority -> stringJoiner.add(authority.getAuthority()));
        }
        return stringJoiner.toString();
    }

    private boolean isTokenInDB(String token, String type) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(Util.generateRedisKey(type, token)));
    }


}
