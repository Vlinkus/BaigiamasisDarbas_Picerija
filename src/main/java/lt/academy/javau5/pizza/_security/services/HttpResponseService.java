package lt.academy.javau5.pizza._security.services;

import lt.academy.javau5.pizza._security.dto_response.AbstractResponse;
import lt.academy.javau5.pizza._security.dto_response.ErrorResponse;
import lt.academy.javau5.pizza._security.dto_response.MsgResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
public class HttpResponseService {

    public ResponseEntity<AbstractResponse> error(HttpStatus status, String msg) {
        return ResponseEntity.status( status )
                .body(new ErrorResponse( status.value() ,"Bad credentials"));
    }

    public ResponseEntity<AbstractResponse> msg(String text) {
        return ResponseEntity.ok(new MsgResponse(text));
    }

    public boolean isBearer(String authHeader) {
        return authHeader == null || !authHeader.startsWith("Bearer ");
    }
}
