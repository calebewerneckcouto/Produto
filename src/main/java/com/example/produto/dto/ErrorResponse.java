package com.example.produto.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<CampoErro> campos) {

    public record CampoErro(String campo, String mensagem) {
    }

    public static ErrorResponse of(int status, String error, String message, String path) {
        return new ErrorResponse(LocalDateTime.now(), status, error, message, path, null);
    }

    public static ErrorResponse of(int status, String error, String message, String path, List<CampoErro> campos) {
        return new ErrorResponse(LocalDateTime.now(), status, error, message, path, campos);
    }
}
