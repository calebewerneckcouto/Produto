package com.example.produto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.produto.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("""
            SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
            FROM Produto p
            WHERE LOWER(p.nome) = LOWER(:nome)
            """)
    boolean existsByNomeIgnoreCase(@Param("nome") String nome);

    @Query("""
            SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
            FROM Produto p
            WHERE LOWER(p.nome) = LOWER(:nome) AND p.id <> :id
            """)
    boolean existsByNomeIgnoreCaseAndIdNot(@Param("nome") String nome, @Param("id") Long id);
}
