package lt.academy.javau5.pizza._security.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")// Adjust the mapping as needed
                .allowedOrigins("http://localhost:3000") // Allow requests from this origin
                .allowedMethods("*") // Allow specific HTTP methods
                .allowedHeaders("*")
                .allowCredentials(true); // Allow cookies and controllers headers
    }
}