package lt.academy.javau5.pizza._security.authentication;

import lombok.RequiredArgsConstructor;
import lt.academy.javau5.pizza._security.entities.User;
import lt.academy.javau5.pizza._security.repositories.UserRepository;
import lt.academy.javau5.pizza._security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/s/")
@RequiredArgsConstructor
public class DemoController {
    private final UserService userService;

    @GetMapping("/hello")
    public String sayHello() {
        return "Labas!";
    }

    @GetMapping("/users")
    public List<User> usersList() {
        return userService.getAll();
    }
}

