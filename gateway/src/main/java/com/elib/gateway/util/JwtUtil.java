package com.elib.gateway.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.elib.gateway.dto.AuthUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


public class JwtUtil {

    private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    // Expire time is 1 hour
    private final static long EXPIRE_TIME = 60 * 60 * 1000;


    public static boolean verify(String token,String username,String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            logger.error("JWT claims string is not correct: {}", e.getMessage());
        }
        return false;
    }

    public static AuthUserDto getUser(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return new AuthUserDto(jwt.getClaim("username").asString(),jwt.getClaim("role").asString());
        } catch (JWTDecodeException e) {
            return null;
        }
    }

}
