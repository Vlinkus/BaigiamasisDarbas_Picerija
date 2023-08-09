package lt.academy.javau5.pizza._security.authentication;

import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lt.academy.javau5.pizza._security.dto_request.AuthenticationRequest;
import lt.academy.javau5.pizza._security.dto_request.RegisterRequest;
import lt.academy.javau5.pizza._security.dto_response.*;
import lt.academy.javau5.pizza._security.services.TokenService;
import lt.academy.javau5.pizza._security.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;
    private final TokenService tokenService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AbstractResponse> register(
            @Valid @RequestBody RegisterRequest request,
            BindingResult bindingResult)
    {
        HttpStatus status;
        if (bindingResult.hasFieldErrors() || bindingResult.hasGlobalErrors()) {
            status = HttpStatus.NOT_ACCEPTABLE; // status 406
            List<ObjectError> err = bindingResult.getAllErrors();
            Set<String> response = new HashSet<>();
            for (ObjectError x: err)
                response.add(x.getDefaultMessage());
            return ResponseEntity.status( status )
                    .body(new MultiErrorResponse( status.value(), response.stream().toList() )) ;
        }

        if ( userService.hasEmail( request.getEmail() ) ) {
            status = HttpStatus.BAD_REQUEST; // status 400
            return ResponseEntity.status( status )
                    .body(new ErrorResponse( status.value() ,"Email is taken"));
        } else if ( userService.hasUsername( request.getUsername() ) ) {
            status = HttpStatus.BAD_REQUEST; // status 400
            return ResponseEntity.status( status )
                    .body(new ErrorResponse( status.value() ,"Username is taken"));
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

    @GetMapping("/role")
//    @ResponseBody
    public ResponseEntity<AbstractResponse> roleQuery(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        HttpStatus status;

        if (authHeader == null || !authHeader.startsWith("Bearer ") ) {
            status = HttpStatus.UNAUTHORIZED;
            return ResponseEntity.status( status )
                    .body(new ErrorResponse( status.value() ,"Bad credentials"));
        }

        String jwt = authHeader.substring(7);
        var storedToken = tokenService.findByToken(jwt)
                .orElse(null);
        if (storedToken == null) {
            status = HttpStatus.UNAUTHORIZED;
            return ResponseEntity.status( status )
                    .body(new ErrorResponse( status.value() ,"Token not found"));
        } else {
            return ResponseEntity.ok(new MsgResponse(storedToken.getToken()));
        }
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null && authentication.getCredentials() instanceof String) {
//            Jwt jwt = (Jwt) authentication.getPrincipal();
//            String token = (String) authentication.getCredentials();
//
//            // You can include the token in the response
//            return "This is a secure endpoint. Token: " + token;
//            //return tokenService.getUserByToken(token).get().getRole().toString();
//        }
//        return "Access denied.";
    }
}
