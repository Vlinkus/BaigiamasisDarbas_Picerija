package lt.academy.javau5.pizza._security.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lt.academy.javau5.pizza._security.dto_request.AuthenticationRequest;
import lt.academy.javau5.pizza._security.dto_request.RegisterRequest;
import lt.academy.javau5.pizza._security.dto_response.AbstractResponse;
import lt.academy.javau5.pizza._security.dto_response.AuthenticationResponse;
import lt.academy.javau5.pizza._security.dto_response.ErrorResponse;
import lt.academy.javau5.pizza._security.dto_response.MultiErrorResponse;
import lt.academy.javau5.pizza._security.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AbstractResponse> register(
            @Valid @RequestBody RegisterRequest request,
            BindingResult bindingResult)
    {
        HttpStatus status;
        if (bindingResult.hasFieldErrors() || bindingResult.hasGlobalErrors()) {
            status = HttpStatus.NOT_ACCEPTABLE;
            List<ObjectError> err = bindingResult.getAllErrors();
            Set<String> response = new HashSet<>();
            for (ObjectError x: err)
                response.add(x.getDefaultMessage());
            return ResponseEntity.status( status )
                    .body(new MultiErrorResponse( status.value(), response.stream().toList() )) ;
        }

        if ( userService.hasEmail( request.getEmail() ) ) {
            status = HttpStatus.NOT_ACCEPTABLE;
            return ResponseEntity.status( status )
                    .body(new ErrorResponse( status.value() ,"User with such email already exists"));
        } else if ( userService.hasUsername( request.getUsername() ) ) {
            status = HttpStatus.NOT_ACCEPTABLE;
            return ResponseEntity.status( status )
                    .body(new ErrorResponse( status.value() ,"Username already taken"));
        }

        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authService.refreshToken(request, response);
    }
}
