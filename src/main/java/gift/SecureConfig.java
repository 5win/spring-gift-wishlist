package gift;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecureConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorize -> {
                authorize
                    .requestMatchers(HttpMethod.GET, "/api/products/**")
                    .hasAuthority("read")
                    .requestMatchers(HttpMethod.POST, "/api/products/product/**")
                    .hasAuthority("write")
                    .anyRequest().authenticated();
            });

//        http.formLogin(Customizer.withDefaults());
        return http.build();
    }
}
