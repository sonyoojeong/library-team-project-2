package com.library.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 로그인이 필요한 메시지를 설정하고 로그인 페이지로 리다이렉트
        request.getSession().setAttribute("message", "로그인이 필요합니다");
        response.sendRedirect("/members/login");

    }
}
