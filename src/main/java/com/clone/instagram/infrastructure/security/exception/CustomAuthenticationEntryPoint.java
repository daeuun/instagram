package com.clone.instagram.infrastructure.security.exception;

import com.clone.instagram.exception.BusinessException;
import com.clone.instagram.exception.ErrorCode;
import com.clone.instagram.dto.error.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.OutputStream;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Object errorObject = request.getAttribute("businessException");

        if (errorObject instanceof BusinessException) {
            ErrorCode errorCode = ErrorCode.valueOf(((BusinessException) errorObject).getMessage());
            sendError(response, errorCode);
        }
    }

    private void sendError(HttpServletResponse response, ErrorCode errorCode) {
        response.setStatus(errorCode.getStatus());
        response.setContentType("application/json,charset=utf-8");
        try (OutputStream os = response.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, ErrorResponse.of(errorCode));
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
