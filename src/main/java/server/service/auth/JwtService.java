package server.service.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import server.config.JwtConstants;
import server.dto.UserDTO;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtConstants constants;

    @Value("${jwt.key}")
    private String secretKey;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String generateToken(UserDTO user, long expiration) {
        return Jwts.builder()
                .subject(user.getLogin())
                .claim("id", user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateAccessToken(UserDTO user) {
        return generateToken(user, constants.getAccessExpiration());
    }

    public String generateRefreshToken(UserDTO user) {
        return generateToken(user, constants.getRefreshExpiration());
    }

    public String getLogin(String token) {
        return getClaim(token)
                .getPayload()
                .getSubject();
    }

    private boolean isTokenExpired(String token) {
        return getClaim(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    public Long getId(String token) {
        return getClaim(token)
                .getPayload()
                .get("id", Long.class);
    }

    public boolean isTokenValid(String token, String login) {
        try {
            return login.equals(getLogin(token)) && !isTokenExpired(token);
        } catch (JwtException e) {
            return false;
        }
    }

    private Jws<Claims> getClaim(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);
    }
}
