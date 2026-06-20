package com.example.produto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI openAPI() {
        final String oauth2Scheme = "oauth2";

        return new OpenAPI()
                .info(new Info()
                        .title("API Produto")
                        .description("API REST de produtos protegida com OAuth2")
                        .version("1.0")
                        .contact(new Contact().name("Produto API")))
                .addSecurityItem(new SecurityRequirement().addList(oauth2Scheme))
                .components(new Components()
                        .addSecuritySchemes(oauth2Scheme, new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .flows(new OAuthFlows()
                                        .clientCredentials(new OAuthFlow()
                                                .tokenUrl("/oauth2/token")
                                                .scopes(produtoScopes()))
                                        .authorizationCode(new OAuthFlow()
                                                .authorizationUrl("/oauth2/authorize")
                                                .tokenUrl("/oauth2/token")
                                                .scopes(produtoScopes())))));
    }

    private Scopes produtoScopes() {
        return new Scopes()
                .addString("openid", "OpenID Connect")
                .addString("produto.read", "Leitura de produtos")
                .addString("produto.write", "Escrita de produtos");
    }
}
