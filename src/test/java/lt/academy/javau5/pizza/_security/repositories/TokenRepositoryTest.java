package lt.academy.javau5.pizza._security.repositories;

import lombok.RequiredArgsConstructor;
import lt.academy.javau5.pizza._security.configuration.ApplicationConfigTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@RequiredArgsConstructor
class TokenRepositoryTest {
    private final TokenRepository tokenRepo;

    @Test
    void findAllValidTokenByUser() {
    }

    @Test
    void findByToken() {

        // Arrange

        // Act

        // Assert

    }

    @Test
    void getUserByToken() {
    }
}