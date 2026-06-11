package com.flashcart.flashcart_backend.security;

import com.flashcart.flashcart_backend.user.entity.User;
import com.flashcart.flashcart_backend.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final DefaultAuthenticationEventPublisher authenticationEventPublisher;

    public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository, DefaultAuthenticationEventPublisher authenticationEventPublisher) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.authenticationEventPublisher = authenticationEventPublisher;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        String token = authHeader.replace("Bearer ","");
        String email  = jwtService.extractUsername(token);
        User user =   userRepository.findByEmail(email);
        if(user!= null && jwtService.isTokenValid(token,user)){
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken( user,null, java.util.List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_"+user.getRole().name())));
            SecurityContextHolder.getContext().setAuthentication( authentication);
        }
        filterChain.doFilter(request,response);
    }


}
