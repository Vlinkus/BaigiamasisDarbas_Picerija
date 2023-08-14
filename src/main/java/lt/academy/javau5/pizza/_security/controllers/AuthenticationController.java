package lt.academy.javau5.pizza._security.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lt.academy.javau5.pizza._security.dto_request.AuthenticationRequest;
import lt.academy.javau5.pizza._security.dto_request.RegisterRequest;
import lt.academy.javau5.pizza._security.dto_response.AbstractResponse;
import lt.academy.javau5.pizza._security.services.AuthenticationService;
import lt.academy.javau5.pizza._security.services.HttpResponseService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/refresh-token")
    public ResponseEntity<AbstractResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        return authService.refreshTokenFromCookie(request, response);
    }

    // not working wothout @CrossOrigin anotation
    // even with global CORS configuration in CorsConfig.java
    @CrossOrigin
    @GetMapping("/logout")
    public ResponseEntity<AbstractResponse> logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return authService.logout(request, response);
    }
}
