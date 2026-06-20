package com.example.produto.controller;

import com.example.produto.entity.Produto;
import com.example.produto.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoControllerTest {

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private ProdutoController controller;

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto("Notebook", 3500.0, 10);
        produto.setId(1L);
    }

    @Test
    void salvarDeveDelegarParaService() {
        when(produtoService.salvar(produto)).thenReturn(produto);

        Produto resultado = controller.salvar(produto);

        assertEquals(produto, resultado);
        verify(produtoService).salvar(produto);
    }

    @Test
    void listarDeveDelegarParaService() {
        List<Produto> produtos = Arrays.asList(produto);
        when(produtoService.listar()).thenReturn(produtos);

        List<Produto> resultado = controller.listar();

        assertEquals(1, resultado.size());
        verify(produtoService).listar();
    }

    @Test
    void buscarDeveDelegarParaService() {
        when(produtoService.buscarPorId(1L)).thenReturn(produto);

        Produto resultado = controller.buscar(1L);

        assertEquals(produto, resultado);
        verify(produtoService).buscarPorId(1L);
    }

    @Test
    void atualizarDeveDelegarParaService() {
        Produto dadosAtualizados = new Produto("Notebook Gamer", 4500.0, 5);
        when(produtoService.atualizar(1L, dadosAtualizados)).thenReturn(produto);

        Produto resultado = controller.atualizar(1L, dadosAtualizados);

        assertEquals(produto, resultado);
        verify(produtoService).atualizar(1L, dadosAtualizados);
    }

    @Test
    void excluirDeveDelegarParaService() {
        controller.excluir(1L);

        verify(produtoService).excluir(1L);
    }
}
