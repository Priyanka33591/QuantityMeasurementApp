package com.app.quantitymeasurement.security.oauth;

import com.app.quantitymeasurement.entity.User;
import com.app.quantitymeasurement.repository.UserRepository;
import com.app.quantitymeasurement.security.jwt.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.oauth2.frontend-redirect:http://localhost:5173/auth/callback}")
    private String frontendRedirect;

    public OAuth2AuthenticationSuccessHandler(
            JwtUtil jwtUtil,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String emailAttr = oauth2User.getAttribute("email");
        if (emailAttr == null || emailAttr.isBlank()) {
            response.sendRedirect(frontendRedirect + "?error=no_email");
            return;
        }
        String email = emailAttr.trim().toLowerCase();

        User user = userRepository.findByEmail(email).orElseGet(() -> createUserFromOAuth(email));

        String token = jwtUtil.generateToken(user.getUsername());
        String url = frontendRedirect
                + "?token=" + URLEncoder.encode(token, StandardCharsets.UTF_8)
                + "&username=" + URLEncoder.encode(user.getUsername(), StandardCharsets.UTF_8);
        response.sendRedirect(url);
    }

    private User createUserFromOAuth(String email) {
        String localPart = email.contains("@") ? email.substring(0, email.indexOf('@')) : email;
        String username = localPart.replaceAll("[^a-zA-Z0-9._-]", "_");
        if (username.isBlank()) {
            username = "user";
        }
        String candidate = username;
        int n = 0;
        while (userRepository.findByUsername(candidate).isPresent()) {
            candidate = username + (++n);
        }

        User u = new User();
        u.setUsername(candidate);
        u.setEmail(email);
        u.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        u.setRole("ROLE_USER");
        return userRepository.save(u);
    }
}
