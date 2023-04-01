package com.clone.instagram.exception;

import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;
    private List<FieldError> errors = new ArrayList<>();

    public BusinessException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, List<FieldError> errors) {
        super(errorCode.getMessage());
        this.errors = errors;
        this.errorCode = errorCode;
    }
}
