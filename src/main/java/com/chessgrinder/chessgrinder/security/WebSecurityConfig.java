package com.chessgrinder.chessgrinder.security;

import com.chessgrinder.chessgrinder.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@SuppressWarnings("Convert2MethodRef")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true
)
@Slf4j
public class WebSecurityConfig {

    private static final String HOME_PATH = "/";

    @Autowired
    private CustomOAuth2UserService oauthUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(httpRequests -> httpRequests
                        .anyRequest().permitAll()
                )
                .formLogin(it -> it.defaultSuccessUrl(HOME_PATH))
                .logout(Customizer.withDefaults())
                .httpBasic(it -> it.disable())
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .userInfoEndpoint(it -> it.userService(oauthUserService))
                                .successHandler((request, response, authentication) -> {
                                    log.debug("Successfully authenticated user "+ authentication.getName() +" via oauth2");
                                    if (authentication.getPrincipal() instanceof CustomOAuth2User customOAuth2User) {
                                        userService.processOAuthPostLogin(customOAuth2User);
                                    }
                                    response.sendRedirect(HOME_PATH);
                                })
                                .failureHandler((request, response, exception) -> {
                                    log.warn("Could not login user via oauth2", exception);
                                    response.sendRedirect(HOME_PATH);
                                })
                )
                .cors(it -> it.disable())
                .csrf(it -> it.disable())
                .exceptionHandling(it -> it.defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED), new AntPathRequestMatcher("/**")))
                .build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
