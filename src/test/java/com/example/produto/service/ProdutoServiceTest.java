package com.example.produto.service;

import com.example.produto.entity.Produto;
import com.example.produto.exception.ProdutoDuplicadoException;
import com.example.produto.exception.ProdutoNaoEncontradoException;
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
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository repository;

    @InjectMocks
    private ProdutoService service;

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto("Notebook", 3500.0, 10);
        produto.setId(1L);
    }

    @Test
    void salvarDevePersistirERetornarProduto() {
        when(repository.existsByNomeIgnoreCase("Notebook")).thenReturn(false);
        when(repository.save(any(Produto.class))).thenReturn(produto);

        Produto novo = new Produto("Notebook", 3500.0, 10);
        Produto resultado = service.salvar(novo);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Notebook", resultado.getNome());
        verify(repository).save(novo);
    }

    @Test
    void salvarDeveLancarExcecaoQuandoProdutoDuplicado() {
        when(repository.existsByNomeIgnoreCase("Notebook")).thenReturn(true);

        Produto novo = new Produto("Notebook", 3500.0, 10);

        assertThrows(ProdutoDuplicadoException.class, () -> service.salvar(novo));
        verify(repository, never()).save(any());
    }

    @Test
    void listarDeveRetornarTodosProdutos() {
        Produto produto2 = new Produto("Mouse", 89.90, 50);
        produto2.setId(2L);

        when(repository.findAll()).thenReturn(Arrays.asList(produto, produto2));

        List<Produto> resultado = service.listar();

        assertEquals(2, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void buscarPorIdDeveRetornarProdutoQuandoEncontrado() {
        when(repository.findById(1L)).thenReturn(Optional.of(produto));

        Produto resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repository).findById(1L);
    }

    @Test
    void buscarPorIdDeveLancarExcecaoQuandoNaoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontradoException.class, () -> service.buscarPorId(99L));
    }

    @Test
    void atualizarDeveModificarProdutoExistente() {
        Produto dadosAtualizados = new Produto("Notebook Gamer", 4500.0, 5);

        when(repository.findById(1L)).thenReturn(Optional.of(produto));
        when(repository.existsByNomeIgnoreCaseAndIdNot("Notebook Gamer", 1L)).thenReturn(false);
        when(repository.save(any(Produto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Produto resultado = service.atualizar(1L, dadosAtualizados);

        assertNotNull(resultado);
        assertEquals("Notebook Gamer", resultado.getNome());
        assertEquals(4500.0, resultado.getPreco());
        assertEquals(5, resultado.getQuantidade());
        verify(repository).save(produto);
    }

    @Test
    void atualizarDeveLancarExcecaoQuandoProdutoNaoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontradoException.class,
                () -> service.atualizar(99L, new Produto("X", 1.0, 1)));
        verify(repository, never()).save(any());
    }

    @Test
    void atualizarDeveLancarExcecaoQuandoNomeDuplicado() {
        Produto dadosAtualizados = new Produto("Mouse", 89.90, 50);

        when(repository.findById(1L)).thenReturn(Optional.of(produto));
        when(repository.existsByNomeIgnoreCaseAndIdNot("Mouse", 1L)).thenReturn(true);

        assertThrows(ProdutoDuplicadoException.class, () -> service.atualizar(1L, dadosAtualizados));
        verify(repository, never()).save(any());
    }

    @Test
    void excluirDeveRemoverProdutoPorId() {
        when(repository.existsById(1L)).thenReturn(true);

        service.excluir(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void excluirDeveLancarExcecaoQuandoProdutoNaoExiste() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThrows(ProdutoNaoEncontradoException.class, () -> service.excluir(99L));
        verify(repository, never()).deleteById(any());
    }
}
