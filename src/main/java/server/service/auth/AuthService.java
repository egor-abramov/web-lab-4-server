package server.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import server.dto.AuthTokens;
import server.dto.UserDTO;
import server.dto.request.AuthRequest;
import server.exception.InvalidTokenException;
import server.exception.UserAlreadyExistsException;
import server.exception.UserNotFoundException;
import server.mapper.UserMapper;
import server.service.user.UserService;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final RefreshTokenService refreshTokenService;

    public void register(AuthRequest request) {
        if (userService.isUserExists(request.getLogin())) {
            throw new UserAlreadyExistsException("User with user name: " + request.getLogin() + " already exists");
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        UserDTO user = userMapper.toDTO(request);
        user.setPassword(encodedPassword);

        try {
            userService.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException("User already exists");
        }
    }

    public AuthTokens login(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getLogin(),
                request.getPassword()
        ));

        UserDTO user = userService.getByLogin(request.getLogin());
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        refreshTokenService.save(user.getLogin(), refreshToken);
        return new AuthTokens(accessToken, refreshToken);
    }

    public AuthTokens refresh(String refreshToken) {
        String login = jwtService.getLogin(refreshToken);
        if (!refreshToken.equals(refreshTokenService.getByLogin(login))) {
            throw new UserNotFoundException("Token revoked");
        }
        if (!jwtService.isTokenValid(refreshToken, login)) {
            throw new InvalidTokenException("Invalid token");
        }

        UserDTO user = userService.getByLogin(login);
        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);
        refreshTokenService.deleteByLogin(user.getLogin());
        refreshTokenService.save(user.getLogin(), newRefreshToken);
        return new AuthTokens(newAccessToken, newRefreshToken);
    }

    public void logout(UserDTO user) {
        refreshTokenService.deleteByLogin(user.getLogin());
    }
}
