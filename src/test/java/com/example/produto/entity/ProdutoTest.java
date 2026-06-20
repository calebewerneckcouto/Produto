package com.example.produto.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    @Test
    void construtorPadraoDeveCriarInstancia() {
        Produto produto = new Produto();

        assertNotNull(produto);
        assertNull(produto.getId());
        assertNull(produto.getNome());
        assertNull(produto.getPreco());
        assertNull(produto.getQuantidade());
    }

    @Test
    void construtorComParametrosDeveInicializarCampos() {
        Produto produto = new Produto("Notebook", 3500.0, 10);

        assertEquals("Notebook", produto.getNome());
        assertEquals(3500.0, produto.getPreco());
        assertEquals(10, produto.getQuantidade());
    }

    @Test
    void settersDevemAtualizarCampos() {
        Produto produto = new Produto();

        produto.setId(1L);
        produto.setNome("Mouse");
        produto.setPreco(89.90);
        produto.setQuantidade(50);

        assertEquals(1L, produto.getId());
        assertEquals("Mouse", produto.getNome());
        assertEquals(89.90, produto.getPreco());
        assertEquals(50, produto.getQuantidade());
    }

    @Test
    void equalsDeveCompararPorId() {
        Produto produto1 = new Produto("A", 10.0, 1);
        produto1.setId(1L);

        Produto produto2 = new Produto("B", 20.0, 2);
        produto2.setId(1L);

        Produto produto3 = new Produto("A", 10.0, 1);
        produto3.setId(2L);

        assertEquals(produto1, produto2);
        assertNotEquals(produto1, produto3);
        assertNotEquals(produto1, null);
        assertEquals(produto1, produto1);
    }

    @Test
    void hashCodeDeveSerConsistenteComEquals() {
        Produto produto1 = new Produto("A", 10.0, 1);
        produto1.setId(1L);

        Produto produto2 = new Produto("B", 20.0, 2);
        produto2.setId(1L);

        assertEquals(produto1.hashCode(), produto2.hashCode());
    }
}
