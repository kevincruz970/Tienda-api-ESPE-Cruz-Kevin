package com.espe.tienda.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime timestamp,
        int estado,
        String error,
        String mensaje) {
}