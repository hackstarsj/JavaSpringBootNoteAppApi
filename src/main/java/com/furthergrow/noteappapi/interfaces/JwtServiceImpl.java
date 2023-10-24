package com.furthergrow.noteappapi.interfaces;

import com.furthergrow.noteappapi.entity.User;
import com.furthergrow.noteappapi.services.JwtService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.KeyStore;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String jwtSigningKey;
    @Override
    public String extractUserName(String token) {
        extractClaimAll(token);
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String extractUserEmail(String token) {
        return (String) extractClaimAll(token).get("email");
    }

    @Override
    public String extractUserNameExact(String token) {
        return (String) extractClaimAll(token).get("username");
    }

    @Override
    public String generateToken(User user,UserDetails userDetails) {
        Map<String,Object> map=new HashMap<>();
        return generateToken(map,user, userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        final String userNameExact = extractUserNameExact(token);
        return (userNameExact.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Map<String, Object> extractClaimAll(String token) {
        final Claims claims = extractAllClaims(token);
        HashMap<String,Object> map=new HashMap<>();
        for (String key : claims.keySet()) {
            map.put(key, claims.get(key));
        }
        return map;
     }

    private String generateToken(Map<String, Object> extraClaims, User user, UserDetails userDetails) {
        byte[] base64Bytes = Base64.getEncoder().encode(jwtSigningKey.getBytes());
        String base64String = new String(base64Bytes);

        extraClaims.put("username",userDetails.getUsername());
        extraClaims.put("email",user.getEmail());
        extraClaims.put("id",user.getId());
        return Jwts.builder().setClaims(extraClaims).setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(SignatureAlgorithm.HS256,base64String).compact();
    }

    public  String generateRefreshToken(User user) {

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + 30L * 24 * 60 * 60 * 1000); // 30 days
        byte[] base64Bytes = Base64.getEncoder().encode(jwtSigningKey.getBytes());
        String base64String = new String(base64Bytes);

        Map<String,Object> extraClaims=new HashMap<>();
        extraClaims.put("username",user.getUsername());
        extraClaims.put("email",user.getEmail());
        extraClaims.put("id",user.getId());

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256,base64String)
                .compact();
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        extractClaimAll(token);
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        byte[] base64Bytes = Base64.getEncoder().encode(jwtSigningKey.getBytes());
        String base64String = new String(base64Bytes);

        try {
            return Jwts.parser()
                    .setSigningKey(base64String)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            // Handle signature validation failure
            e.printStackTrace(); // You can log or handle this error as needed.
            return null; // or throw a custom exception
        } catch (MalformedJwtException e) {
            // Handle malformed JWT
            e.printStackTrace(); // You can log or handle this error as needed.
            return null; // or throw a custom exception
        } catch (AccessDeniedException e){
            e.printStackTrace();
            return null;
        }
        catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace(); // You can log or handle this error as needed.
            return null; // or throw a custom exception
        }
    }

}