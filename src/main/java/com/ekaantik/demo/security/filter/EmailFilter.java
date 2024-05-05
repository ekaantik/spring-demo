// package com.ekaantik.demo.security.filter;

// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import lombok.extern.slf4j.Slf4j;

// import java.io.IOException;

// @Component
// @Slf4j
// public class EmailFilter extends OncePerRequestFilter {
//     // TODO : Filter Email
//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//             throws ServletException, IOException {
//         String requestBody = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
//         filterChain.doFilter(request, response);
//     }

//     @Override
//     protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//         String path = request.getRequestURI();
//         return !path.startsWith("/aurora/api/v1/auth/register") && !path.startsWith("/aurora/api/v1/auth/authenticate");
//     }
// }