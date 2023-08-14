package lt.academy.javau5.pizza._security.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lt.academy.javau5.pizza._security.dto_request.AuthenticationRequest;
import lt.academy.javau5.pizza._security.dto_request.RegisterRequest;
import lt.academy.javau5.pizza._security.dto_response.AbstractResponse;
import lt.academy.javau5.pizza._security.dto_response.AuthenticationResponse;
import lt.academy.javau5.pizza._security.dto_response.BroadAuthenticationResponse;
import lt.academy.javau5.pizza._security.dto_response.ErrorResponse;
import lt.academy.javau5.pizza._security.dto_response.MsgResponse;
import lt.academy.javau5.pizza._security.entities.Role;
import lt.academy.javau5.pizza._security.entities.Token;
import lt.academy.javau5.pizza._security.entities.TokenType;
import lt.academy.javau5.pizza._security.entities.User;
import lt.academy.javau5.pizza._security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public AuthenticationResponse register(RegisterRequest request, HttpServletResponse response) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .username(request.getUsername())
                .email(request.getEmail())
                .password( passwordEncoder.encode(request.getPassword()) )
                .role( request.getRole() == null ? Role.USER : request.getRole() )
                .build();

        var savedUser = repository.save(user);
        var accessToken = jwtService.buildAccessToken(user);
        var refreshToken = jwtService.buildRefreshToken(user);
        addRefreshTokenCookie(response, refreshToken);
        saveUserToken(savedUser, accessToken);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    public AbstractResponse authenticate(AuthenticationRequest request, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        var accessToken = jwtService.buildAccessToken(user);
        var refreshToken = jwtService.buildRefreshToken(user);
        revokeAllUserTokens(user);
        addRefreshTokenCookie(response, refreshToken);
        saveUserToken(user, accessToken);
        return BroadAuthenticationResponse.builder()
                .accessToken(accessToken)
                .role(user.getRole().name())
                .build();
    }

    public ResponseEntity<AbstractResponse> logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            var storedToken = tokenService.findByToken(jwt)
                    .orElse(null);
            if (storedToken != null) {
                storedToken.setExpired(true);
                storedToken.setRevoked(true);
                tokenService.save(storedToken);
            }
        }

        jwtService.deleteRefreshTokenCookie(response);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(new MsgResponse("Ok"));
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenService.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenService.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenService.saveAll(validUserTokens);
    }

    public ResponseEntity<AbstractResponse> refreshTokenFromCookie(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String refreshToken = jwtService.extractRefreshTokenFromCookie(request);
        if (refreshToken != null) {
            String username = jwtService.extractUsername(refreshToken);
            if (username != null) {
                User user = repository.findByUsername(username).orElse(null);
                if (user != null && jwtService.isTokenValid(refreshToken, user)) {
                    String accessToken = jwtService.buildAccessToken(user);
                    String newRefreshToken = jwtService.buildRefreshToken(user);
                    addRefreshTokenCookie(response, newRefreshToken);
                    return createTokensResponse(accessToken, user.getRole().name());
                }
            }
        }
        return ResponseEntity.status(FORBIDDEN) // status 403
                .body(new ErrorResponse(FORBIDDEN.value(), "Forbidden"));
    }

    public void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true); // prevent js from accessing the cookie
        cookie.setMaxAge((int) (refreshExpiration) / 1000); // cookie's max age within seconds
        response.addCookie(cookie);
    }

    private ResponseEntity<AbstractResponse> createTokensResponse(String accessToken, String role) {
        return ResponseEntity.ok(new BroadAuthenticationResponse(accessToken,role));

    }
}
