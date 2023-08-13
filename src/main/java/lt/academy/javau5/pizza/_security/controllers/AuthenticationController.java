package lt.academy.javau5.pizza._security.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lt.academy.javau5.pizza._security.dto_request.AuthenticationRequest;
import lt.academy.javau5.pizza._security.dto_request.RegisterRequest;
import lt.academy.javau5.pizza._security.dto_response.*;
import lt.academy.javau5.pizza._security.entities.User;
import lt.academy.javau5.pizza._security.services.AuthenticationService;
import lt.academy.javau5.pizza._security.services.HttpResponseService;
import lt.academy.javau5.pizza._security.services.JwtService;
import lt.academy.javau5.pizza._security.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;
    private final HttpResponseService responseService;

    @PostMapping("/register")
    public ResponseEntity<AbstractResponse> register(
            @Valid @RequestBody RegisterRequest request,
            HttpServletResponse response,
            BindingResult bindingResult)
    {
        var error = responseService.regisrationRequestValidation(bindingResult, request);
        if (error != null)
            return error;
        return ResponseEntity.status(CREATED).body( authService.register(request, response) );
    }

    @PostMapping("/login")
    public ResponseEntity<AbstractResponse> authenticate(
            @RequestBody AuthenticationRequest request,
            HttpServletResponse response)
    {
        return ResponseEntity.ok(authService.authenticate(request, response));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AbstractResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        return authService.refreshTokenFromCookie(request, response);
    }
}
