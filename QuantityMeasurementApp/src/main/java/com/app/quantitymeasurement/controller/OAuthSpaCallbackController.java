package com.app.quantitymeasurement.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * The React OAuth callback route is served by Vite ({@code :5173/auth/callback}). If the
 * browser or Google settings send {@code GET /auth/callback} to the API ({@code :8080}),
 * Spring would otherwise try to serve a static file and return 404. This controller forwards
 * to the SPA with the same query string (token/username from our success handler).
 * <p>
 * If Google sends an authorization {@code code} here, the OAuth client was misconfigured:
 * the redirect URI must be {@code /login/oauth2/code/google}, not {@code /auth/callback}.
 */
@Controller
public class OAuthSpaCallbackController {

    @Value("${app.oauth2.frontend-redirect:http://localhost:5173/auth/callback}")
    private String frontendCallback;

    @Value("${app.oauth2.frontend-login-url:http://localhost:5173/login}")
    private String frontendLogin;

    @GetMapping("/auth/callback")
    public RedirectView oauthCallback(HttpServletRequest request) {
        String code = request.getParameter("code");
        if (code != null && !code.isBlank()) {
            String msg =
                    "Google OAuth redirect URI is wrong. In Google Cloud Console use exactly "
                            + "http://localhost:8080/login/oauth2/code/google — not /auth/callback.";
            return new RedirectView(
                    frontendLogin + "?error=" + URLEncoder.encode(msg, StandardCharsets.UTF_8));
        }

        String qs = request.getQueryString();
        if (qs == null || qs.isEmpty()) {
            return new RedirectView(frontendCallback);
        }
        String sep = frontendCallback.contains("?") ? "&" : "?";
        return new RedirectView(frontendCallback + sep + qs);
    }
}
