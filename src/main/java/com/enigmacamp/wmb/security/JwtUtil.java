package com.enigmacamp.wmb.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtUtil {

//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String jwtSecret = "secret";
    private final String appName = "Warung Makan Bahari";

    public String generateToken(String userId) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
            String token = JWT.create()
                    .withIssuer(appName)
                    .withExpiresAt(Instant.now().plusSeconds(60 * 60))
                    .withIssuedAt(Instant.now())
                    .withClaim("role", "ROLE_CUSTOMER")
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException e){
            log.error("error while create jwt token", e.getMessage());
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
            log.error("Invalid verifikation JWT: ", e. getMessage());
            return false;
        }
    }

    public Map<String, Object> getUserInfoByToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("userId", decodedJWT.getSubject());
            userInfo.put("role", decodedJWT.getClaim("role"));
            return userInfo;
        } catch (JWTVerificationException e){
            log.error("Invalid verifikation JWT: ", e.getMessage());
            return null;
        }
    }
}
