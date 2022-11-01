package spring.security.oauth2.resourceserver.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(exchanges -> exchanges.pathMatchers("/maxmadam/v1/agreement/**").authenticated()
                        .pathMatchers("/maxmadam/v1/customer/**").authenticated()
                        .pathMatchers("/maxmadam/v1/agreementadmin/**").authenticated()
                        .pathMatchers("/maxmadam/v1/ucinstance/**").authenticated())
                .oauth2ResourceServer().jwt();
        http.csrf().disable();
        return http.build();
    }

 

}
