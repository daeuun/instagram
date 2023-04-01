package com.clone.instagram.dto.error;

import com.clone.instagram.exception.ErrorCode;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import javax.validation.ConstraintViolation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class ErrorResponse {
    private int status;
    private String code;
    private String message;
    private List<FieldError> errors;

    public ErrorResponse(ErrorCode code, List<FieldError> errors) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.errors = errors;
        this.code = code.getCode();
    }

    public ErrorResponse(ErrorCode code) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
        this.errors = new ArrayList<>();
    }

    public static ErrorResponse of(ErrorCode code, BindingResult bindingResult) {
        return new ErrorResponse(code, FieldError.of(bindingResult));
    }

    public static ErrorResponse of(ErrorCode code, Set<ConstraintViolation<?>> constraintViolations) {
        return new ErrorResponse(code, FieldError.of(constraintViolations));
    }

    public static ErrorResponse of(ErrorCode code, String missingParameterName) {
        return new ErrorResponse(code, FieldError.of(missingParameterName, "", "parameter가 필요합니다"));
    }

    public static ErrorResponse of(ErrorCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(ErrorCode code, List<FieldError> errors) {
        return new ErrorResponse(code, errors);
    }

    public static ErrorResponse of(MethodArgumentTypeMismatchException e) {
        String value = e.getValue() == null ? "" : e.getValue().toString();
        List<FieldError> errors = FieldError.of(e.getName(), value, e.getErrorCode());
        return new ErrorResponse(ErrorCode.INVALID_TYPE_VALUE, errors);
    }
}

class FieldError {
    private final String field;
    private final String value;
    private final String reason;

    public FieldError(String field, String value, String reason) {
        this.field = field;
        this.value = value;
        this.reason = reason;
    }

    public static List<FieldError> of(String field, String value, String reason) {
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError(field, value, reason));
        return fieldErrors;
    }

    public static List<FieldError> of(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(error -> new FieldError(
                        error.getField(),
                        error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                        error.getDefaultMessage()
                ))
                .collect(Collectors.toList());
    }

    public static List<FieldError> of(Set<ConstraintViolation<?>> constraintViolations) {
        return constraintViolations.stream()
                .map(error -> new FieldError(
                        error.getPropertyPath().toString(),
                        "",
                        error.getMessageTemplate()
                ))
                .collect(Collectors.toList());
    }
}