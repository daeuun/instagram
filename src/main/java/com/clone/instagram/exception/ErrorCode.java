package com.clone.instagram.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(500, "ERROR_COMMON_C001", "내부 서버 오류입니다."),
    INVALID_INPUT_VALUE(400, "ERROR_COMMON_C002", "유효하지 않은 입력입니다."),
    METHOD_NOT_ALLOWED(405, "ERROR_COMMON_C003", "허용되지 않은 HTTP method 입니다."),
    INVALID_TYPE_VALUE(400, "ERROR_COMMON_C004", "입력 타입이 유효하지 않습니다."),

    // User
    NO_AUTHORITY(403, "U001", "권한이 없습니다."),
    USER_DOES_NOT_EXISTS(404, "U002", "회원 정보를 찾을수 없습니다."),

    // jwt
    INVALID_REFRESH_TOKEN(401, "J001", "유효하지 않은 Refresh Token 입니다."),

    ;

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
