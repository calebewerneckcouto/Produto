package com.example.produto.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.produto.dto.UsuarioRequest;
import com.example.produto.dto.UsuarioResponse;
import com.example.produto.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Cadastro e dados do usuário logado")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registro")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar novo usuário")
    public UsuarioResponse registrar(@Valid @RequestBody UsuarioRequest request) {
        return usuarioService.registrar(request);
    }

    @GetMapping("/me")
    @SecurityRequirement(name = "oauth2")
    @Operation(summary = "Dados do usuário autenticado")
    public UsuarioResponse me(@AuthenticationPrincipal UserDetails userDetails) {
        return usuarioService.buscarPorUsername(userDetails.getUsername());
    }
}
