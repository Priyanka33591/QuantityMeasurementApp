package com.app.quantitymeasurement.security.oauth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Sends users back to the React app when Google OAuth fails, instead of Spring's
 * default HTML page at {@code /login?error} on port 8080.
 */
@Component
public class OAuth2LoginFrontendFailureHandler implements AuthenticationFailureHandler {

    @Value("${app.oauth2.frontend-login-url:http://localhost:5173/login}")
    private String frontendLoginUrl;

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException {

        String code = "oauth_failed";
        String detail = exception.getMessage();

        if (exception instanceof OAuth2AuthenticationException oae) {
            OAuth2Error err = oae.getError();
            if (err != null) {
                if (err.getErrorCode() != null && !err.getErrorCode().isBlank()) {
                    code = err.getErrorCode();
                }
                if (err.getDescription() != null && !err.getDescription().isBlank()) {
                    detail = err.getDescription();
                }
            }
        }

        if (detail != null) {
            String d = detail.toLowerCase();
            if (d.contains("redirect_uri") || d.contains("redirect uri")) {
                code = "redirect_uri_mismatch";
            } else if (d.contains("invalid_client") || d.contains("client_secret")) {
                code = "invalid_client";
            } else if (d.contains("access_denied")) {
                code = "access_denied";
            }
        }

        String value = code + (detail != null && !detail.equals(code) ? ": " + detail : "");
        String url =
                frontendLoginUrl
                        + "?error="
                        + URLEncoder.encode(value, StandardCharsets.UTF_8);
        response.sendRedirect(url);
    }
}
