package com.anizzzz.product.sssweaterhouse.security.jwtutil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -3301605591108950415L;
    private Clock clock= DefaultClock.INSTANCE;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String getUsernameFromToken(String token){ return getClaimFromToken(token, Claims::getSubject);}

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = new ArrayList<>();
        userDetails.getAuthorities().forEach(auth->roles.add(auth.getAuthority()));
        claims.put("roles",roles);
        return doGenerateToken(claims, userDetails.getUsername());
    }

    public boolean canTokenBeRefreshed(String token, Date lastPasswordReset){
        final Date created = getIssuedDateAtDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public String refreshToken(String token){
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails){
        JwtUser user = (JwtUser) userDetails;
        final String username= getUsernameFromToken(token);

        return (username.equalsIgnoreCase(user.getUsername()) && !isTokenExpired(token));
    }

    private Date getIssuedDateAtDateFromToken(String token){return getClaimFromToken(token, Claims::getIssuedAt);}

    private Date getExpirationDateFromToken(String token){return getClaimFromToken(token, Claims::getExpiration);}

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResovler){
        final Claims claims=getAllClaimsFromToken(token);
        return claimsResovler.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(clock.now());
    }

    private boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset){
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private boolean ignoreTokenExpiration(String token){
        // here you specify token, for that expiration is ignored
        return false;
    }

    private String doGenerateToken(Map<String, Object> claims, String subject){
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Date calculateExpirationDate(Date createdDate){
        return new Date(createdDate.getTime() + expiration * 1000);
    }
}
