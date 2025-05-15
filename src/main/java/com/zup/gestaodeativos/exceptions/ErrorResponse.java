package com.zup.gestaodeativos.exceptions;

public record ErrorResponse(
        String code,
        String message,
        String description,
        String error
) {}