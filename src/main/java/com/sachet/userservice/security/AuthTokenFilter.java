package com.sachet.userservice.security;

import com.sachet.userservice.service.JwtService;
import com.sachet.userservice.service.UserSecurityDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AuthenticationSecurityManager authenticationSecurityManager;
    private final UserSecurityDetailsService userSecurityDetailsService;

    public AuthTokenFilter(JwtService jwtService, AuthenticationSecurityManager authenticationSecurityManager, UserSecurityDetailsService userSecurityDetailsService) {
        this.jwtService = jwtService;
        this.authenticationSecurityManager = authenticationSecurityManager;
        this.userSecurityDetailsService = userSecurityDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwtToken = parseJwt(request);
        if (jwtToken != null && jwtService.validateToken(jwtToken)) {
            final String username = jwtService.extractUsername(jwtToken);
            final UserDetails userDetails = userSecurityDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    jwtToken,
                    userDetails.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext()
                    .setAuthentication(authenticationSecurityManager.authenticate(authenticationToken));
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String headerPrefix = "Bearer ";
        if (authHeader != null && authHeader.startsWith(headerPrefix)) {
            return authHeader.substring(headerPrefix.length());
        }
        return null;
    }
}
