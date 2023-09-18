package com.example.ibproekt2.security.config;

import jakarta.annotation.Resource;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    @Resource
    private DataSource dataSource;
    @Resource
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                                .requestMatchers("/home").permitAll()
                                .requestMatchers("/home/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/categories/**", "/manufacturers/**").hasRole("ADMIN")
                                .requestMatchers("/products/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/endpoint").permitAll()
                                .requestMatchers("/register", "/login", "verify", "password/**").anonymous()
                                .anyRequest().authenticated()
                )
                //Ova tuka e za da se forsira https konekcija na site requests na https://localhost:8443
                //Deka koristev self signed certificate dava warning, realno treba da se upotrebi normalen SSL sertifikat
//                .requiresChannel(x -> x
//                        .requestMatchers("/**").requiresSecure()
//                )
                .authenticationProvider(authenticationProvider)
                .rememberMe((x) ->
                        x.tokenRepository(persistentTokenRepository()).userDetailsService(userDetailsService)
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/perform_login")
                        .defaultSuccessUrl("/home", true)
                        .failureForwardUrl("/login_failure")
                        .failureUrl("/login?error")
                )
                .logout(logout -> logout
                        .deleteCookies("remember-me")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/home")
                )
                .sessionManagement((session) -> {
//                            session.maximumSessions(1);
                            session.invalidSessionUrl("/login?invalid_session=true");
                        }
                );


        return http.build();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }

}
