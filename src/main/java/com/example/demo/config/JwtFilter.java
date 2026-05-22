package com.example.demo.config;

import com.example.demo.repository.UserRepository;
import com.example.demo.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Достаём заголовок Authorization
        String header = request.getHeader("Authorization");

        // 2. Если заголовка нет — пропускаем (Security сам разберётся)
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Достаём токен из заголовка
        String token = header.substring(7); // убираем "Bearer "

        // 4. Достаём username из токена
        String username = jwtService.extractUsername(token);

        // 5. Проверяем пользователя и токен
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            userRepository.findByUsername(username).ifPresent(user -> {
                if (jwtService.isValid(token, user.getUsername())) {
                    // 6. Говорим Spring что пользователь авторизован
                    var auth = new UsernamePasswordAuthenticationToken(
                            user, null, List.of()
                    );
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            });
        }

        // 7. Передаём запрос дальше
        filterChain.doFilter(request, response);
    }
}