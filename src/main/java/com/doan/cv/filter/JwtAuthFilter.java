package com.doan.cv.filter;

import com.doan.cv.entity.Permission;
import com.doan.cv.entity.User;
import com.doan.cv.repository.UserRepository;
import com.doan.cv.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtil jwTutil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private static final List<String> whiteList = List.of("/auth/account", "/auth/logout");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String email = this.jwTutil.extractUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);


            User user = userRepository.findByEmailWithRoleAndPermissions(email).orElse(null);

            if (user == null || user.getRole() == null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "unexpected ROLE");
                return;
            }
            List<Permission> permissionList = user.getRole().getPermissions();
            String path = request.getServletPath();
            String method = request.getMethod();

            //allow to perform fetch current account and logout action
            if(whiteList.stream().anyMatch(p -> pathMatcher.match(p,path))){
                filterChain.doFilter(request,response);
                return;
            }

            boolean isAllowed = permissionList.stream()
                                              .anyMatch(p -> pathMatcher.match(p.getApiPath(),path)
                                                                       && p.getMethod().equalsIgnoreCase(method));
            if (!isAllowed) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "you aren't not allowed to access this API");
                return;
            }
        }
        filterChain.doFilter(request,response);
    }
}
