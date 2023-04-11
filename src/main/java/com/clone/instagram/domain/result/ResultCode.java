package com.clone.instagram.domain.result;

public enum ResultCode {
    // Users
    SIGN_UP_SUCCESS(200, "U001", "회원가입 성공"),
    LOGIN_SUCCESS(200, "U002", "로그인 성공"),
    GET_USER_PROFILE_SUCCESS(200, "U003", "회원 프로필 조회 성공"),
    GET_MY_PROFILE_SUCCESS(200, "U004", "내 프로필 조회 성공"),
    USER_WITHDRAW_SUCCESS(200, "U005", "회원 탈퇴 성공"),
    UPDATE_PROFILE_SUCCESS(200, "U006", "프로필 업데이트 성공"),
    USER_FOLLOW_SUCCESS(200, "U007", "회원 팔로우 성공"),
    GET_USER_PROFILE_SUCCESSFULLY(200, "U008", "프로필 조회 성공"),
    UPDATE_MY_PROFILE_SUCCESSFULLY(200, "U009", "프로필 수정 성공"),

    // Posts,
    POST_CREATED_SUCCESSFULLY(200, "P001", "글 작성 성공"),
    POST_UPDATE_SUCCESSFULLY(200, "P002", "글 수정 성공"),
    GET_POSTS_SUCCESSFULLY(200, "P003", "글 조회 성공"),
    DELETE_POST_SUCCESSFULLY(200, "P004", "글 삭제 성공"),

    // Comment,
    CREATE_COMMENT_SUCCESSFULLY(200, "C001", "댓글 작성 성공"),
    GET_COMMENTS_SUCCESSFULLY(200, "C002", "댓글 조회 성공"),
    UPDATE_COMMENT_SUCCESSFULLY(200, "C003", "댓글 수정 성공");


    public final int status;
    public final String code;
    public final String message;

    ResultCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
