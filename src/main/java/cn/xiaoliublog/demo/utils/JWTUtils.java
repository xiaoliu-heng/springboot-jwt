package cn.xiaoliublog.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Configurable
public class JWTUtils {
    private static String secret;
    @Value("${jwt.secret}")
    public void setSecret(String secret){
        JWTUtils.secret = secret;
    }

    private static String issuer;
    @Value("${jwt.issuer}")
    public void setIssuer(String issuer) {
        JWTUtils.issuer = issuer;
    }

    private static Long expires;
    @Value("${jwt.expires:3600000}")
    public void setExpires(Long expires) {
        JWTUtils.expires = expires;
    }

    public String getToken(String username, String password) {
        return JWT.create()
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .withExpiresAt(new Date(new Date().getTime()+expires))
                .withClaim("username", username)
                .withClaim("password", password)
                .sign(Algorithm.HMAC256(secret));
    }

    public DecodedJWT verify(String token) throws JWTVerificationException{
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).acceptExpiresAt(5).withIssuer(issuer).build();
        return verifier.verify(token);
    }

    public DecodedJWT decode(String token){
        return JWT.decode(token);
    }

}
