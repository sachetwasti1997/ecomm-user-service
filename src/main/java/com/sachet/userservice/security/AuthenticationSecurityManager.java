package com.sachet.userservice.security;

import com.sachet.userservice.service.JwtService;
import lombok.SneakyThrows;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSecurityManager implements AuthenticationManager {

    private final JwtService jwtService;

    public AuthenticationSecurityManager(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    @NonNull
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication authentication1 = createAuthentication(authentication);
        if (authentication1 != null)
            return authentication1;
        throw new AuthenticateEx("User is not authenticated");
    }

    private Authentication createAuthentication(Authentication authentication) {
        String token = (String) authentication.getCredentials();
        return jwtService.validateToken(token) ? authentication : null;
    }
}

class AuthenticateEx extends AuthenticationException {

    public AuthenticateEx(@Nullable String msg) {
        super(msg);
    }
}
