package com.example.produto.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.produto.entity.Produto;
import com.example.produto.exception.ProdutoDuplicadoException;
import com.example.produto.exception.ProdutoNaoEncontradoException;
import com.example.produto.repository.ProdutoRepository;

@Service
@Transactional(readOnly = true)
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Produto salvar(Produto produto) {
        validarNomeUnico(produto.getNome(), null);
        return repository.save(produto);
    }

    public List<Produto> listar() {
        return repository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }

    @Transactional
    public Produto atualizar(Long id, Produto produto) {
        Produto existente = repository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));

        validarNomeUnico(produto.getNome(), id);

        existente.setNome(produto.getNome());
        existente.setPreco(produto.getPreco());
        existente.setQuantidade(produto.getQuantidade());

        return repository.save(existente);
    }

    @Transactional
    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new ProdutoNaoEncontradoException(id);
        }
        repository.deleteById(id);
    }

    private void validarNomeUnico(String nome, Long id) {
        boolean duplicado = id == null
                ? repository.existsByNomeIgnoreCase(nome)
                : repository.existsByNomeIgnoreCaseAndIdNot(nome, id);

        if (duplicado) {
            throw new ProdutoDuplicadoException(nome);
        }
    }
}
