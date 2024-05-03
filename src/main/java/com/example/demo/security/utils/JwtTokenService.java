package com.example.demo.security.utils;

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
//    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

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
        //                    .setExpiration(cal.getTime()) //a java.util.Date
//                    .setIssuedAt(today) // for example, now

        // Create JWT claims containing user-related information
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("roles", user.getUserType());

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
            log.info("####### JwtTokenService : validateToken ");
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey()).build()
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty");
        } catch (SignatureException e) {
            log.error("there is an error with the signature of you token ");
        }
        return false;
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSigningKey.getBytes(StandardCharsets.UTF_8));

//        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
//        return Keys.hmacShaKeyFor(keyBytes);
        //SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
//        final SecretKey signingKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
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

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public UUID extractClaimsCustId(String token) {
        return UUID.fromString((String) extractAllClaims(token).get("custId"));
    }
}


//    public String generateJwtKey(User user, CustomerDetails customerDetails, String role) {
//        Date today = new Date();
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(today);
//        cal.add(Calendar.YEAR, 1);
//
//        try {
//            final SecretKey signingKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
//            return Jwts.builder()
//                    .setIssuer("Glokal")
//                    .setSubject("token")
//                    .setAudience("you")
//                    .claim("userId", user != null ? user.getId() : null)
//                    .claim("businessId", business != null ? business.getId() : null)
//                    .claim("user", user != null ? userMapper.basicDto(user) : null)
//                    .claim("business", business != null ? businessMapper.toBasicModel(business) : null)
//                    .claim("roles", role)
//                    .setExpiration(cal.getTime()) //a java.util.Date
//                    .setIssuedAt(today) // for example, now
//                    .setId(UUID.randomUUID().toString())
//                    .signWith(signingKey)
//                    .compact();
//        } catch (Exception ex) {
//            logger.error("jwt token generation failed", ex);
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "token generation failed with reason " + ex.getMessage());
//        }
//    }


//    public String createAuthToken(VerifyOtpReq req) throws Exception {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(req.getUserMobileNumber(), "")
//            );
//        }
//        catch (BadCredentialsException e) {
//            throw new Exception("Incorrect username or password", e);
//        }
////        final UserDetails userDetails = userDetailsService
////                .loadUserByUsername(req.getUserMobileNumber());
////        Generate Token
////        jwtTokenUtil.generateToken(userDetails);
//        return null;
//    }

//        Calendar cal = Calendar.getInstance();
//        cal.setTime(today);
//        cal.add(Calendar.YEAR, 1);
//
//        try {
//      final SecretKey signingKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
//            return Jwts.builder()
//                    .setIssuer("Glokal")
//                    .setSubject("token")
//                    .setAudience("you")
//                    .claim("userId", user != null ? user.getId() : null)
//                    .claim("businessId", business != null ? business.getId() : null)
//                    .claim("user", user != null ? userMapper.basicDto(user) : null)
//                    .claim("business", business != null ? businessMapper.toBasicModel(business) : null)
//                    .claim("roles", role)
//                    .setExpiration(cal.getTime()) //a java.util.Date
//                    .setIssuedAt(today) // for example, now
//                    .setId(UUID.randomUUID().toString())
//                    .signWith(signingKey)
//                    .compact();

//                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
//                .signWith(SignatureAlgorithm.HS512, key)
//                .signWith(keyLocal)
