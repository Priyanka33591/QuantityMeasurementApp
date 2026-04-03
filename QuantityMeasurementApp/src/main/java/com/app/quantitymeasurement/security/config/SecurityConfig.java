package com.app.quantitymeasurement.security.config;

import com.app.quantitymeasurement.security.jwt.JwtFilter;
import com.app.quantitymeasurement.security.oauth.OAuth2AuthenticationSuccessHandler;
import com.app.quantitymeasurement.security.oauth.OAuth2LoginFrontendFailureHandler;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2LoginFrontendFailureHandler oAuth2LoginFrontendFailureHandler;

    public SecurityConfig(
            JwtFilter jwtFilter,
            OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler,
            OAuth2LoginFrontendFailureHandler oAuth2LoginFrontendFailureHandler) {
        this.jwtFilter = jwtFilter;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
        this.oAuth2LoginFrontendFailureHandler = oAuth2LoginFrontendFailureHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                // ✅ H2 console fix
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/**",
                                "/api/v1/quantities/auth/**",
                                "/oauth2/**",
                                "/login/oauth2/**",
                                "/login",
                                "/h2-console/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/v1/quantities").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/quantities/perform").permitAll()

                        .anyRequest().authenticated()
                )

                .formLogin(AbstractHttpConfigurer::disable)

                .oauth2Login(oauth2 -> oauth2
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                        .failureHandler(oAuth2LoginFrontendFailureHandler)
                )

                // ⭐ REQUIRED for OAuth (DO NOT USE STATELESS)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                org.springframework.security.config.http.SessionCreationPolicy.IF_REQUIRED
                        )
                )

                // ✅ JWT filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}