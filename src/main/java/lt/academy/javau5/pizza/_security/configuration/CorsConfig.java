package lt.academy.javau5.pizza._security.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
    private static final String frontEndServer = "http://localhost:3000";

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/v1/auth/**")// Adjust the mapping as needed
                .allowedOrigins(frontEndServer) // Allow requests from this origin
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow specific HTTP methods
                .allowCredentials(true); // Allow cookies and authentication headers

    }
}