package com.example.produto.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final com.example.produto.repository.ProdutoRepository repository;

    public ProdutoController(com.example.produto.repository.ProdutoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public com.example.produto.entity.Produto salvar(@RequestBody com.example.produto.entity.Produto produto) {
        return repository.save(produto);
    }

    @GetMapping
    public List<com.example.produto.entity.Produto> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public com.example.produto.entity.Produto buscar(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public com.example.produto.entity.Produto atualizar(@PathVariable Long id,
                             @RequestBody com.example.produto.entity.Produto produto) {

        com.example.produto.entity.Produto p = repository.findById(id).orElse(null);

        if (p != null) {
            p.setNome(produto.getNome());
            p.setPreco(produto.getPreco());
            p.setQuantidade(produto.getQuantidade());

            return repository.save(p);
        }

        return null;
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        repository.deleteById(id);
    }
}