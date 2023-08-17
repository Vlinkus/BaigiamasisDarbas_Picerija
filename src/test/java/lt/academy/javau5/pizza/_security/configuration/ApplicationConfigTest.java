package lt.academy.javau5.pizza._security.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@TestConfiguration
public class ApplicationConfigTest {

    @Test
    void userDetailsService() {
    }

    @Test
    void authenticationProvider() {
    }

    @Test
    void authenticationManager() {
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}