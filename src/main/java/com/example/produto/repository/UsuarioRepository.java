package com.example.produto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.produto.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("""
            SELECT u
            FROM Usuario u
            WHERE u.username = :username
            """)
    Optional<Usuario> findByUsername(@Param("username") String username);

    @Query("""
            SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END
            FROM Usuario u
            WHERE u.username = :username
            """)
    boolean existsByUsername(@Param("username") String username);
}
