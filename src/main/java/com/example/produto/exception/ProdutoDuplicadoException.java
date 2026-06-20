package com.example.produto.exception;

public class ProdutoDuplicadoException extends RuntimeException {

    public ProdutoDuplicadoException(String nome) {
        super("Produto já cadastrado com o nome: " + nome);
    }
}
