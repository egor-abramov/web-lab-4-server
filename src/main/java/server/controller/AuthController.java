package server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import server.config.JwtConstants;
import server.dto.AuthTokens;
import server.dto.UserDTO;
import server.dto.request.AuthRequest;
import server.dto.response.AuthResponse;
import jakarta.validation.Valid;
import server.service.auth.AuthService;
import server.service.auth.RefreshTokenService;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtConstants jwtConstants;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        AuthTokens authTokens = authService.login(request);
        ResponseCookie refreshCookie = refreshTokenService.generateRefreshCookie(authTokens.getRefreshToken());
        AuthResponse responseBody = new AuthResponse(
                authTokens.getAccessToken(),
                jwtConstants.getAccessExpirationSeconds()
        );

        return ResponseEntity.ok()
                .header(SET_COOKIE, refreshCookie.toString())
                .body(responseBody);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid AuthRequest request) {
        authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User registered");
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@CookieValue(name = "refresh_token") String refreshToken) {
        AuthTokens authTokens = authService.refresh(refreshToken);
        ResponseCookie refreshCookie = refreshTokenService.generateRefreshCookie(authTokens.getRefreshToken());
        AuthResponse responseBody = new AuthResponse(
                authTokens.getAccessToken(),
                jwtConstants.getAccessExpirationSeconds()
        );

        return ResponseEntity.ok()
                .header(SET_COOKIE, refreshCookie.toString())
                .body(responseBody);
    }

    @PostMapping
    public ResponseEntity<String> logout(@AuthenticationPrincipal UserDTO user) {
        authService.logout(user);
        ResponseCookie dropRefreshCookie = refreshTokenService.dropRefreshCookie();

        return ResponseEntity.ok()
                .header(SET_COOKIE, dropRefreshCookie.toString())
                .build();
    }
}
