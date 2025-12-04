package server.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import server.config.JwtConstants;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final StringRedisTemplate redis;
    private final JwtConstants jwtConstants;

    public void save(String login, String token) {
        redis.opsForValue().set(
                login,
                token,
                jwtConstants.getRefreshExpirationSeconds(),
                TimeUnit.SECONDS
        );
    }

    public void deleteByLogin(String login) {
        redis.delete(login);
    }

    public String getByLogin(String login) {
        return redis.opsForValue().get(login);
    }

    public ResponseCookie generateRefreshCookie(String refreshToken) {
        return ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(jwtConstants.getRefreshExpirationSeconds())
                .sameSite("Strict")
                .build();
    }

    public ResponseCookie dropRefreshCookie() {
        return ResponseCookie
                .from("refresh_token", "")
                .maxAge(0)
                .build();
    }
}
