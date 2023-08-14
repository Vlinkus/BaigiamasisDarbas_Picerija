package lt.academy.javau5.pizza._security.services;

import lombok.RequiredArgsConstructor;
import lt.academy.javau5.pizza._security.dto_request.RegisterRequest;
import lt.academy.javau5.pizza._security.dto_response.AbstractResponse;
import lt.academy.javau5.pizza._security.dto_response.ErrorResponse;
import lt.academy.javau5.pizza._security.dto_response.MsgResponse;
import lt.academy.javau5.pizza._security.dto_response.MultiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@Service
@RequiredArgsConstructor
public class HttpResponseService {
    private final UserService userService;

    public ResponseEntity<AbstractResponse> error(HttpStatus status, String msg) {
        return ResponseEntity.status( status )
                .body(new ErrorResponse( status.value() ,msg));
    }

    public ResponseEntity<AbstractResponse> msg(String text) {
        return ResponseEntity.ok(new MsgResponse(text));
    }

    public boolean isBearer(String authHeader) {
        return authHeader == null || !authHeader.startsWith("Bearer ");
    }

    public ResponseEntity<AbstractResponse> regisrationRequestValidation(
            BindingResult bindingResult, RegisterRequest request)
    {
            var error = isRegRequestValid(bindingResult, request);
            if (error != null)
                return error;

            error = isUsernameTaken(request);
            if (error != null)
                return error;

            error = isEmailTaken(request);
            return error; // returns "null" once username is not taken
    }

    public ResponseEntity<AbstractResponse> isRegRequestValid(BindingResult bindingResult, RegisterRequest request) {
        HttpStatus status;
        if (bindingResult.hasFieldErrors() || bindingResult.hasGlobalErrors()) {
            status = NOT_ACCEPTABLE; // status 406
            List<ObjectError> err = bindingResult.getAllErrors();
            Set<String> errorResponse = new HashSet<>();
            for (ObjectError x: err)
                errorResponse.add( x.getDefaultMessage() );
            return ResponseEntity.status( status )
                    .body(new MultiErrorResponse( status.value(), errorResponse.stream().toList() )) ;
        }
        return null;
    }

    public ResponseEntity<AbstractResponse> isEmailTaken(RegisterRequest request) {
        if ( userService.hasUsername( request.getUsername() ) ) {
            HttpStatus status = BAD_REQUEST; // status 400
            return ResponseEntity.status( status )
                    .body(new ErrorResponse( status.value() ,"Username is taken"));
        }
        return null;
    }

    public ResponseEntity<AbstractResponse> isUsernameTaken(RegisterRequest request) {
        if ( userService.hasEmail( request.getEmail() ) ) {
            HttpStatus status = BAD_REQUEST; // status 400
            return ResponseEntity.status( status )
                    .body(new ErrorResponse( status.value() ,"Email is taken"));
        }
        return null;
    }
}
