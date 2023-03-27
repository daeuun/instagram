package com.clone.instagram.infrastructure.security.exception;

import com.clone.instagram.dto.error.ErrorResponse;
import com.clone.instagram.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.io.OutputStream;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        sendError(response, ErrorCode.NO_AUTHORITY);
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
