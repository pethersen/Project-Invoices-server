package cz.itnetwork.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * Overrides the default CORS configuration to allow cross-origin requests for all endpoints.
     * @param registry A registry for CORS configuration.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Call the super method to retain default CORS configuration
        WebMvcConfigurer.super.addCorsMappings(registry);

        // Define CORS mappings for all endpoints
        registry.addMapping("/**")
                // Define allowed HTTP methods
                .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS")
                // Define allowed origin patterns
                .allowedOriginPatterns("**")
                // Specify whether the browser should include any cookies associated
                // with the request (if true, credentials are sent).
                .allowCredentials(true);
    }
}
