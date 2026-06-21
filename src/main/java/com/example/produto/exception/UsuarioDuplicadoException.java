package com.example.produto.exception;

public class UsuarioDuplicadoException extends RuntimeException {

    public UsuarioDuplicadoException(String username) {
        super("Usuário já cadastrado: " + username);
    }
}
