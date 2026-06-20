package com.example.produto.handler;

import com.example.produto.exception.ProdutoDuplicadoException;
import com.example.produto.exception.ProdutoNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import com.example.produto.dto.ErrorResponse;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
        request = new MockHttpServletRequest();
        request.setRequestURI("/produtos");
    }

    @Test
    void deveRetornar409ParaProdutoDuplicado() {
        ResponseEntity<ErrorResponse> response = handler.handleProdutoDuplicado(
                new ProdutoDuplicadoException("Notebook"), request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(409, response.getBody().status());
        assertTrue(response.getBody().message().contains("Notebook"));
    }

    @Test
    void deveRetornar404ParaProdutoNaoEncontrado() {
        ResponseEntity<ErrorResponse> response = handler.handleProdutoNaoEncontrado(
                new ProdutoNaoEncontradoException(1L), request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().status());
    }
}
