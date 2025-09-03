package com.example.crud.config;

import com.example.crud.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtService jwtService;

    private UserDetailsService userDetailsService;
    
    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }
    
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        
        System.out.println("=== JWT Filter Debug ===");
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Method: " + request.getMethod());
        System.out.println("Auth Header: " + (authHeader != null ? authHeader.substring(0, Math.min(20, authHeader.length())) + "..." : "null"));
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("No valid auth header found, continuing...");
            filterChain.doFilter(request, response);
            return;
        }
        
        jwt = authHeader.substring(7);
        
        try {
            userEmail = jwtService.extractEmail(jwt);
            System.out.println("Extracted email: " + userEmail);
            
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                try {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                    System.out.println("User details loaded: " + userDetails.getUsername());
                    
                    boolean isValid = jwtService.validateToken(jwt, userDetails.getUsername());
                    System.out.println("Token validation result: " + isValid);
                    
                    if (isValid) {
                        System.out.println("Token validated successfully");
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        
                        authToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );
                        
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        System.out.println("Authentication set in context");
                    } else {
                        System.out.println("Token validation failed - clearing context");
                        SecurityContextHolder.clearContext();
                    }
                } catch (Exception userDetailsException) {
                    System.err.println("Error loading user details: " + userDetailsException.getMessage());
                    userDetailsException.printStackTrace();
                    SecurityContextHolder.clearContext();
                }
            } else {
                System.out.println("User email is null or already authenticated");
                if (SecurityContextHolder.getContext().getAuthentication() != null) {
                    System.out.println("Current authentication: " + SecurityContextHolder.getContext().getAuthentication().getName());
                }
            }
        } catch (Exception e) {
            System.err.println("Error processing JWT token: " + e.getMessage());
            e.printStackTrace();
            SecurityContextHolder.clearContext();
        }
        
        System.out.println("=== End JWT Filter Debug ===");
        filterChain.doFilter(request, response);
    }
}
