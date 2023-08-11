package lt.academy.javau5.pizza._security.authentication;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lt.academy.javau5.pizza._security.dto_response.AbstractResponse;
import lt.academy.javau5.pizza._security.entities.Role;
import lt.academy.javau5.pizza._security.entities.Token;
import lt.academy.javau5.pizza._security.entities.User;
import lt.academy.javau5.pizza._security.services.HttpResponseService;
import lt.academy.javau5.pizza._security.services.TokenService;
import lt.academy.javau5.pizza._security.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("/api/v2")
@RequiredArgsConstructor
public class DemoController {
    private final TokenService tokenService;
    private final HttpResponseService responseService;

    @GetMapping("/hello")
    public String sayHello() {
        return "Labas!";
    }

    @GetMapping("/role")
    public ResponseEntity<AbstractResponse> checkRole(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (responseService.isBearer(authHeader))
            return responseService.error(UNAUTHORIZED,"Bad credentials 1");

        String jwt = authHeader.substring(7);
        Token token = tokenService.findByToken(jwt).orElse(null);
        if (token == null)
            return responseService.error(UNAUTHORIZED,"Token not found");

        User user = tokenService.getUserByToken(jwt).orElse(null);
        if (user == null)
            return responseService.error(UNAUTHORIZED,"Bad credentials 2");

        String role = user.getRole().name();
        return responseService.msg(role) ;
    }

/*    @GetMapping("/query")
    @ResponseBody
    public ResponseEntity<AbstractResponse> checkUniversal(HttpServletRequest request, @RequestParam String[] values) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ") )
            return responseService.error(UNAUTHORIZED,"Bad credentials 1");

        String jwt = authHeader.substring(7);
        Token token = tokenService.findByToken(jwt).orElse(null);
        if (token == null)
            return responseService.error(UNAUTHORIZED,"Token not found");

        User user = tokenService.getUserByToken(jwt).orElse(null);
        if (user == null)
            return responseService.error(UNAUTHORIZED,"Bad credentials 2");

        String role = user.getRole().name();
        return responseService.msg(role) ;
    }*/
}

