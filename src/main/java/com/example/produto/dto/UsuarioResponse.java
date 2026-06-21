package com.example.produto.dto;

import com.example.produto.entity.Usuario;

public record UsuarioResponse(Long id, String username, String role) {

    public static UsuarioResponse from(Usuario usuario) {
        return new UsuarioResponse(usuario.getId(), usuario.getUsername(), usuario.getRole());
    }
}
