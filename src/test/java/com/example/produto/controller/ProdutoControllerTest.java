package com.example.produto.controller;

import com.example.produto.entity.Produto;
import com.example.produto.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoControllerTest {

    @Mock
    private ProdutoRepository repository;

    @InjectMocks
    private ProdutoController controller;

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto("Notebook", 3500.0, 10);
        produto.setId(1L);
    }

    @Test
    void salvarDevePersistirERetornarProduto() {
        when(repository.save(any(Produto.class))).thenReturn(produto);

        Produto novo = new Produto("Notebook", 3500.0, 10);
        Produto resultado = controller.salvar(novo);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Notebook", resultado.getNome());
        verify(repository).save(novo);
    }

    @Test
    void listarDeveRetornarTodosProdutos() {
        Produto produto2 = new Produto("Mouse", 89.90, 50);
        produto2.setId(2L);

        when(repository.findAll()).thenReturn(Arrays.asList(produto, produto2));

        List<Produto> resultado = controller.listar();

        assertEquals(2, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void buscarDeveRetornarProdutoQuandoEncontrado() {
        when(repository.findById(1L)).thenReturn(Optional.of(produto));

        Produto resultado = controller.buscar(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repository).findById(1L);
    }

    @Test
    void buscarDeveRetornarNullQuandoNaoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Produto resultado = controller.buscar(99L);

        assertNull(resultado);
        verify(repository).findById(99L);
    }

    @Test
    void atualizarDeveModificarProdutoExistente() {
        Produto dadosAtualizados = new Produto("Notebook Gamer", 4500.0, 5);

        when(repository.findById(1L)).thenReturn(Optional.of(produto));
        when(repository.save(any(Produto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Produto resultado = controller.atualizar(1L, dadosAtualizados);

        assertNotNull(resultado);
        assertEquals("Notebook Gamer", resultado.getNome());
        assertEquals(4500.0, resultado.getPreco());
        assertEquals(5, resultado.getQuantidade());
        verify(repository).save(produto);
    }

    @Test
    void atualizarDeveRetornarNullQuandoProdutoNaoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Produto resultado = controller.atualizar(99L, new Produto("X", 1.0, 1));

        assertNull(resultado);
        verify(repository, never()).save(any());
    }

    @Test
    void excluirDeveRemoverProdutoPorId() {
        controller.excluir(1L);

        verify(repository).deleteById(1L);
    }
}
