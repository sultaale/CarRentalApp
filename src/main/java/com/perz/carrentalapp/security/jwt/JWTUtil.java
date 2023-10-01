package com.perz.carrentalapp.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;


@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String secret;

    public String generateToken(Long id, String email) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(1440).toInstant());

        return JWT.create()
                .withSubject("User details")
                .withClaim("id", id)
                .withClaim("email", email)
                .withIssuedAt(new Date())
                .withIssuer("blueTeam")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveEmailClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("blueTeam")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("email").asString();
    }

    public Long validateTokenAndRetrieveIdClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("blueTeam")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("id").asLong();
    }
}
