package com.elib.userclient.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.elib.userclient.model.CustomUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


public class JwtUtil {

    private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    // Expire time is 1 hour
    private final static long EXPIRE_TIME = 60 * 60 * 1000;

    public static String generateToken(CustomUser user) {
        Algorithm algorithm = Algorithm.HMAC256(user.getAccountInfo().getPassword());
        return JWT.create()
                .withClaim("username", user.getAccountInfo().getEmail())
                .withClaim("role",user.getAccountInfo().getRoles().stream().findFirst().get())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .sign(algorithm);
    }

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

    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

}
