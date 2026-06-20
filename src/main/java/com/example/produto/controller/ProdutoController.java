package com.example.produto.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.produto.entity.Produto;
import com.example.produto.repository.ProdutoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Produtos", description = "Operações CRUD de produtos")
@SecurityRequirement(name = "oauth2")
public class ProdutoController {

    private final ProdutoRepository repository;

    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Operation(summary = "Cadastrar produto")
    public Produto salvar(@RequestBody Produto produto) {
        return repository.save(produto);
    }

    @GetMapping
    @Operation(summary = "Listar produtos")
    public List<Produto> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID")
    public Produto buscar(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto")
    public Produto atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        Produto p = repository.findById(id).orElse(null);

        if (p != null) {
            p.setNome(produto.getNome());
            p.setPreco(produto.getPreco());
            p.setQuantidade(produto.getQuantidade());

            return repository.save(p);
        }

        return null;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir produto")
    public void excluir(@PathVariable Long id) {
        repository.deleteById(id);
    }
}