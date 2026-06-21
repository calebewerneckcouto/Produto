package com.example.produto.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.produto.entity.Usuario;
import com.example.produto.repository.UsuarioRepository;

@Configuration
@Profile("!test")
public class UsuarioDataInitializer {

    @Bean
    CommandLineRunner initAdminUser(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!repository.existsByUsername("admin")) {
                repository.save(new Usuario(
                        "admin",
                        passwordEncoder.encode("admin123"),
                        "ROLE_ADMIN"));
            }
        };
    }
}
