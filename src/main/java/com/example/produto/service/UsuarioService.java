package com.example.produto.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.produto.dto.UsuarioRequest;
import com.example.produto.dto.UsuarioResponse;
import com.example.produto.entity.Usuario;
import com.example.produto.exception.UsuarioDuplicadoException;
import com.example.produto.repository.UsuarioRepository;

@Service
@Transactional(readOnly = true)
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

    @Transactional
    public UsuarioResponse registrar(UsuarioRequest request) {
        if (repository.existsByUsername(request.username())) {
            throw new UsuarioDuplicadoException(request.username());
        }

        Usuario usuario = new Usuario(
                request.username(),
                passwordEncoder.encode(request.password()),
                "ROLE_USER");

        return UsuarioResponse.from(repository.save(usuario));
    }

    public UsuarioResponse buscarPorUsername(String username) {
        Usuario usuario = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        return UsuarioResponse.from(usuario);
    }
}
