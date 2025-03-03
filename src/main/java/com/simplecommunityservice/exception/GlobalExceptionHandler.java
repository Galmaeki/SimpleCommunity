package com.simplecommunityservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ResponseException> handlingRuntimeException(RuntimeException e) {
        log.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 에러"));
    }

    @ExceptionHandler
    public ResponseEntity<ResponseException> handlingDBException(DataAccessException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body((new ResponseException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "db 에러!")));
    }

    @ExceptionHandler
    public ResponseEntity<ApplicationException> handlingApplicationException(ApplicationException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(new ApplicationException(e.getErrorCode(), e.getMessage()));
    }
}
