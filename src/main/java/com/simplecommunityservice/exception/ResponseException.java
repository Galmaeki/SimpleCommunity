package com.simplecommunityservice.exception;

public record ResponseException(
        Integer status,
        String message
) {
}
