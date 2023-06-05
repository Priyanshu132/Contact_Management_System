package com.losung360.assignment.util;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class Jwt {

    private static final String SECERT = "MY_SECRET_KEY";
    public String createJWT(String id) {


        long time=32400000; // Expired for 9 hours.
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECERT);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject("User Authentication")
                .setIssuer("Priyanshu Gupta")
                .signWith(signatureAlgorithm, signingKey);

        long expMillis = nowMillis + time;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);

        return builder.compact();
    }
}
