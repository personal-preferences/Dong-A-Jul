package org.personal.user_service.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.personal.user_service.user.domain.CustomUserDetails;
import org.personal.user_service.user.domain.User;
import org.personal.user_service.user.etc.ROLE;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;


public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        String accessToken = request.getHeader("access");
        String refreshToken = findRefresh(request);
        System.out.println("accessToken = " + accessToken);
        System.out.println("refreshToken = " + refreshToken);

        try {

            // 엑세스 토큰 검사
            if (accessToken != null && accessToken.startsWith("Bearer ")) {
                accessToken = accessToken.split(" ")[1];
                System.out.println("1");

                if (!jwtUtil.isExpired(accessToken)) {
                    System.out.println("not expired");
                    // 엑세스 토큰이 만료되지 않았다면 다음로직 진행
                    handleAccessToken(accessToken, response, filterChain, request);

                } else if (refreshToken != null) {
                    System.out.println("refresh 토큰 검사");
                    // 엑세스 토큰이 만료되고, 리프레스 토큰이 존재할 때 리프레스 토큰 검사 후 새로운 엑세스 토큰 생성
                    // 다음 로직 진행
                    handleRefreshToken(refreshToken, request, response, filterChain);

                } else {
                    System.out.println("2");

                    // 엑세스 토큰 만료되고, 리프레시 토큰도 없다면 401 반환
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    try (PrintWriter printWriter = response.getWriter()) {
                        printWriter.print("Access token expired and no refresh token provided.");
                        printWriter.flush();
                    }
                }

                // 만약 엑세스 토큰 없다면 리프래시 토큰 검사 후 새로운 엑세스 토큰을 헤더에 담고 응답
            } else if (refreshToken != null) {
                System.out.println("3");
                handleRefreshToken(refreshToken, request, response, filterChain);

            } else {
                System.out.println("4");
                // 둘다 없으면 다음으로 진행하여 Unauthorized로 진행
                filterChain.doFilter(request, response);
            }

        } catch (Exception e) {
            System.out.println("e = " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            try (PrintWriter printWriter = response.getWriter()) {
                printWriter.print("Invalid token.");
                printWriter.flush();
            }
        }
    }

    private void handleAccessToken(String accessToken, HttpServletResponse response, FilterChain filterChain, HttpServletRequest request) throws IOException, ServletException {
        String category = jwtUtil.getCategory(accessToken);
        if (!"access".equals(category)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            try (PrintWriter printWriter = response.getWriter()) {
                printWriter.print("This token is not an access token");
                printWriter.flush();
            }
            return;
        }

        User user = getUserFromToken(accessToken);
        authenticateUser(user);
        filterChain.doFilter(request, response);
    }

    private void handleRefreshToken(String refreshToken, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            if (jwtUtil.isExpired(refreshToken)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                try (PrintWriter printWriter = response.getWriter()) {
                    printWriter.print("Refresh token expired.");
                    printWriter.flush();
                }
                return;
            }

            User user = getUserFromToken(refreshToken);
            String newAccessToken = jwtUtil.createJwt("access", user.getUserEmail(), user.getUserNickname(), user.getUserRole().name(), TOKENTIME.REFRESH.label());
            response.addHeader("access", "Bearer " + newAccessToken);
            authenticateUser(user);
            System.out.println("새로운 access 토큰 반환");
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private User getUserFromToken(String token) {
        String userEmail = jwtUtil.getUserEmail(token);
        String userNickname = jwtUtil.getUserNickname(token);
        String role = jwtUtil.getRole(token);

        return User.builder()
                .userEmail(userEmail)
                .userNickname(userNickname)
                .userRole(ROLE.valueOf(role))
                .build();
    }

    private void authenticateUser(User user) {
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private String findRefresh(HttpServletRequest req) {

        String refresh = null;
        try {
            Cookie[] cookies = req.getCookies();
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("refresh")){
                    refresh = cookie.getValue();
                    break;
                }
            }
        }catch (Exception e){
            return null;
        }
        return  refresh;
    }
}
