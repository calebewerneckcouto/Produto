package com.example.produto.service;

import com.example.produto.dto.UsuarioRequest;
import com.example.produto.entity.Usuario;
import com.example.produto.exception.UsuarioDuplicadoException;
import com.example.produto.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService service;

    @Test
    void registrarDeveCriarUsuario() {
        UsuarioRequest request = new UsuarioRequest("joao", "123456");

        when(repository.existsByUsername("joao")).thenReturn(false);
        when(passwordEncoder.encode("123456")).thenReturn("encoded");
        when(repository.save(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario u = invocation.getArgument(0);
            u.setId(1L);
            return u;
        });

        var response = service.registrar(request);

        assertEquals(1L, response.id());
        assertEquals("joao", response.username());
        assertEquals("ROLE_USER", response.role());
    }

    @Test
    void registrarDeveLancarExcecaoQuandoUsernameDuplicado() {
        when(repository.existsByUsername("joao")).thenReturn(true);

        assertThrows(UsuarioDuplicadoException.class,
                () -> service.registrar(new UsuarioRequest("joao", "123456")));
    }

    @Test
    void loadUserByUsernameDeveRetornarUsuario() {
        Usuario usuario = new Usuario("admin", "encoded", "ROLE_ADMIN");
        when(repository.findByUsername("admin")).thenReturn(Optional.of(usuario));

        var userDetails = service.loadUserByUsername("admin");

        assertEquals("admin", userDetails.getUsername());
    }

    @Test
    void loadUserByUsernameDeveLancarExcecaoQuandoNaoEncontrado() {
        when(repository.findByUsername("inexistente")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("inexistente"));
    }
}
