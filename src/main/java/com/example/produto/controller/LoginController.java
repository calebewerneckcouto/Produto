package com.example.produto.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping(value = "/login", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            CsrfToken csrfToken) {

        String alerta = "";
        if (error != null) {
            alerta = "<p style='color:#b00020'>Usuário ou senha inválidos.</p>";
        } else if (logout != null) {
            alerta = "<p style='color:#006400'>Logout realizado com sucesso.</p>";
        }

        String csrfParam = csrfToken != null ? csrfToken.getParameterName() : "_csrf";
        String csrfValue = csrfToken != null ? csrfToken.getToken() : "";

        String html = """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                    <meta charset="UTF-8"/>
                    <title>Login - Produto API</title>
                    <style>
                        body { font-family: Arial, sans-serif; max-width: 400px; margin: 80px auto; padding: 24px; }
                        h1 { font-size: 1.5rem; }
                        label { display: block; margin-bottom: 12px; }
                        input { width: 100%%; padding: 8px; box-sizing: border-box; }
                        button { margin-top: 8px; padding: 10px 16px; cursor: pointer; }
                    </style>
                </head>
                <body>
                    <h1>Produto API</h1>
                    %s
                    <form method="post" action="/login">
                        <input type="hidden" name="%s" value="%s"/>
                        <label>
                            Usuário
                            <input type="text" name="username" required autofocus/>
                        </label>
                        <label>
                            Senha
                            <input type="password" name="password" required/>
                        </label>
                        <button type="submit">Entrar</button>
                    </form>
                </body>
                </html>
                """.formatted(alerta, csrfParam, csrfValue);

        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(html);
    }
}
