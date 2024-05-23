package com.example.demo.security.utils;

import com.example.demo.constants.ErrorCode;
import com.example.demo.exception.InvalidTokenException;
import com.example.demo.security.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenService {

    @Value("${token.signing.key}")
    private String jwtSigningKey;

    /**
     * Generates a JWT (JSON Web Token) for the given user.
     *
     * @param user The user object for whom the token is generated.
     * @return string representing the generated JWT.
     */
    public String generateToken(User user) {
        // Set token expiration date to 1 year from today
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.YEAR, 1);

        // Extract user authorities to build token claims
        final String authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        // .setExpiration(cal.getTime()) //a java.util.Date
        // .setIssuedAt(today) // for example, now

        List<String> roleList = new ArrayList<>();
        roleList.add(user.getUserType().toString());
        // roleList.add(user.getUserType());
        // Create JWT claims containing user-related information
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("userPn", user.getPhoneNumber());
        claims.put("roles", roleList.toString());

        // Generate JWT token with provided claims and signing key
        String token = Jwts.builder()
                .setIssuer("Demo")
                .setClaims(claims)
                .setExpiration(cal.getTime())
                .setIssuedAt(today)
                .signWith(getSigningKey())
                .compact();
        return token;
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // Check if the token is valid and not expired
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey()).build()
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException
                | SignatureException ex) {
            log.error("Error Validating Token : " + ex.getMessage());
            throw new InvalidTokenException(ErrorCode.INVALID_TOKEN);
        }
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSigningKey.getBytes(StandardCharsets.UTF_8));
    }

    // Extract the username from the JWT token
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()).build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
    }

    public UUID extractClaimsId(String token) {
        return UUID.fromString((String) extractAllClaims(token).get("userId"));
    }

    public String extractPhoneNumber(String token) {
        return (String) extractAllClaims(token).get("userPn");
    }

}