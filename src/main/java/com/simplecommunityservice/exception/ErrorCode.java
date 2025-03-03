package com.simplecommunityservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // COMMON
    NOT_FOUND(HttpStatus.NOT_FOUND, "경로가 올바르지 않습니다"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다."),

    // USER
    USER_NOT_EXIST(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다."),

    // 5xx
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}