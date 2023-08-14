package lt.academy.javau5.pizza._security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("lt.academy.javau5.pizza._security.controllers"))
                .apis(RequestHandlerSelectors.basePackage("lt.academy.javau5.pizza.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Pizzeriaa API Documentation")
                .description("Your API Description")
                .version("1.0")
                .contact(new Contact("Maksimas", "https://github.com/Vlinkus/BaigiamasisDarbas_Picerija", ""))
                .license("Open source")
                .licenseUrl("")
                .termsOfServiceUrl("")
                .build();
    }

}