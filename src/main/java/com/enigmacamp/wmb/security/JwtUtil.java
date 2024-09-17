package com.enigmacamp.wmb.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigmacamp.wmb.entity.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtUtil {

//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${app.warung-makan-bahari.jwt-secret}")
    private String jwtSecret;

    @Value("${app.warung-makan-bahari.app-name}")
    private String appName;

    @Value("${app.warung-makan-bahari.jwtExpirationInSecond}")
    private long jwtExpirationInSecond;

    public String generateToken(AppUser appUser) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
            String token = JWT.create()
                    .withIssuer(appName)
                    .withSubject(appUser.getId())
                    .withExpiresAt(Instant.now().plusSeconds(jwtExpirationInSecond))
                    .withIssuedAt(Instant.now())
                    .withClaim("role", appUser.getRole().name())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException e){
            log.error("error while create jwt token: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean verifyJwtToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getIssuer().equals(appName);
        } catch (JWTVerificationException e){
            log.error("Invalid verifikation JWT: {}", e. getMessage());
            return false;
        }
    }

    public Map<String, String> getUserInfoByToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("userId", decodedJWT.getSubject());
            userInfo.put("role", decodedJWT.getClaim("role").asString());
            return userInfo;
        } catch (JWTVerificationException e){
            log.error("Invalid verifikation JWT: {}", e.getMessage());
            return null;
        }
    }
}
