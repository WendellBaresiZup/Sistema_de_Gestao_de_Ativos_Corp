package com.zup.gestaodeativos.exceptions;

import lombok.Getter;

@Getter
public class UserExceptions extends RuntimeException {
    private final String code;
    private final String description;

    public UserExceptions(String code, String message, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }
}