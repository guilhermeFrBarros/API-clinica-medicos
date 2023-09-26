package med.prometheus.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    private SecurityFilter securityFilter;

    @Bean  // metodo para controlar a autenticação
    public SecurityFilterChain securityFilterChain( HttpSecurity http ) throws  Exception {
        // A partir da versão 3.1 do Spring Boot
        return http.csrf( csrf -> csrf.disable())
                .sessionManagement( sm -> sm.sessionCreationPolicy( SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST,"/login")
                .permitAll().anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean  // serve para mostrar para spring com injetar o  AuthenticationManager
    public AuthenticationManager authenticationManager ( AuthenticationConfiguration configuration ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean // mostra o tipo de criptografia qu vai ser usado
    public PasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }

}
