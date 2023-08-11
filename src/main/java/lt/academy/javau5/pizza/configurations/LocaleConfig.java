//package lt.academy.javau5.pizza.configurations;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.support.ResourceBundleMessageSource;
//import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
//import java.util.Locale;
//
//
//@Configuration
//public class LocaleConfig {
//    @Bean
//    public AcceptHeaderLocaleResolver customLocaleResolver() {
//        final AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
//        resolver.setDefaultLocale(Locale.US);
//        return resolver;
//    }
//    @Bean
//    public ResourceBundleMessageSource messageSource() {
//        final ResourceBundleMessageSource source = new ResourceBundleMessageSource();
//        source.setBasename("internationalization/language");
//        return source;
//    }
//}