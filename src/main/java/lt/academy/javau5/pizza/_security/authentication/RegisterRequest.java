package lt.academy.javau5.pizza._security.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.academy.javau5.pizza._security.entities.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstname;
    private String lastname;
//    private String username;
    private String email;
    private String password;
    private Role role;
}
