package lt.academy.javau5.pizza._security.repositories;

import lombok.RequiredArgsConstructor;
import lt.academy.javau5.pizza._security.configuration.ApplicationConfigTest;
import lt.academy.javau5.pizza._security.entities.Role;
import lt.academy.javau5.pizza._security.entities.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import static lt.academy.javau5.pizza._security.entities.Role.USER;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@RequiredArgsConstructor
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void injectedComponentsAreNotNull(){
        assertThat(userRepository).isNotNull();
    }

    @Test
    void findById() {
    }

    @Test
    void findByEmail() {

    }

    @Test
    public void testIfFindsByUsername() {
        // Arrange
        User dummy = User.builder()
                .firstname("first")
                .lastname("second")
                .username("harakirity")
                .email("holy@jesus.com")
                .password( new BCryptPasswordEncoder().encode("Justasimplepassword123") )
                .role( USER )
                .build();
        User saved = userRepository.save(dummy);
        // Act
        User foundUser = userRepository.findByUsername("harakirity").orElse(null);

        // Assert
        assertThat(foundUser).isNotNull();
        assertEquals( foundUser.getUsername(), "harakiri" );
    }

    @Test
    void getAllUsernames() {
    }

    @Test
    void existsByEmail() {
    }

    @Test
    void existsByUsername() {
    }
}