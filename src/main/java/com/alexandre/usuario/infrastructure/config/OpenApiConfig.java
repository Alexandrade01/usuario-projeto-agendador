package com.alexandre.usuario.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do OpenAPI (Swagger) para documentação da API.
 * <p>
 * Define informações gerais da API, configuração de segurança JWT e servidores.
 * </p>
 *
 * @author Alexandre
 * @version 1.0
 * @since 2026-06-28
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de Gerenciamento de Usuários - Projeto Agendador",
                version = "1.0.0",
                description = """
                        API REST para gerenciamento completo de usuários, incluindo cadastro, autenticação,
                        e gerenciamento de endereços e telefones.
                        
                        ## Funcionalidades principais:
                        - Cadastro e autenticação de usuários com JWT
                        - CRUD completo de usuários, endereços e telefones
                        - Autenticação e autorização com Spring Security
                        
                        ## Autenticação:
                        Para utilizar os endpoints protegidos, você deve:
                        1. Criar um usuário através do endpoint POST /usuario
                        2. Fazer login através do endpoint POST /usuario/login
                        3. Copiar o token JWT retornado
                        4. Clicar no botão "Authorize" no topo desta página
                        5. Inserir o token no formato: Bearer {seu-token-aqui}
                        """,
                contact = @Contact(
                        name = "Alexandre",
                        email = "alexandre@example.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        servers = {
                @Server(
                        description = "Servidor Local",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Servidor de Desenvolvimento",
                        url = "https://dev.exemplo.com"
                )
        }
)
@SecurityScheme(
        name = "bearer-jwt",
        description = "Token JWT para autenticação. Formato: Bearer {token}",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}

