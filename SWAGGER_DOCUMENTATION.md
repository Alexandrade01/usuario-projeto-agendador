# Documentação Swagger - API de Gerenciamento de Usuários

## 📚 Visão Geral

Este documento descreve como acessar e utilizar a documentação interativa da API através do Swagger/OpenAPI.

## 🚀 Como Acessar a Documentação

### 1. Iniciar o Aplicativo
Primeiro, inicie a aplicação Spring Boot:

```bash
./gradlew bootRun
```

ou no Windows:

```bash
.\gradlew.bat bootRun
```

### 2. Acessar a Interface Swagger UI

Com a aplicação em execução, acesse no seu navegador:

**Swagger UI:** http://localhost:8080/swagger-ui/index.html

**OpenAPI JSON:** http://localhost:8080/v3/api-docs

**OpenAPI YAML:** http://localhost:8080/v3/api-docs.yaml

## 🔐 Autenticação na Documentação

A API utiliza autenticação JWT. Para testar os endpoints protegidos através do Swagger:

### Passo 1: Criar um Usuário
1. No Swagger UI, localize o endpoint **POST /usuario**
2. Clique em **Try it out**
3. Preencha o JSON com os dados do usuário:
```json
{
  "nome": "João da Silva",
  "email": "joao.silva@email.com",
  "senha": "senha123"
}
```
4. Clique em **Execute**

### Passo 2: Fazer Login
1. Localize o endpoint **POST /usuario/login**
2. Clique em **Try it out**
3. Preencha apenas email e senha:
```json
{
  "email": "joao.silva@email.com",
  "senha": "senha123"
}
```
4. Clique em **Execute**
5. **Copie o token JWT retornado** (algo como: `Bearer eyJhbGciOiJIUzI1NiIs...`)

### Passo 3: Autorizar no Swagger
1. No topo da página do Swagger, clique no botão **Authorize** 🔒
2. Cole o token JWT completo (incluindo "Bearer ") no campo
3. Clique em **Authorize**
4. Feche o modal

**Pronto!** Agora você pode testar todos os endpoints protegidos.

## 📖 Organização da Documentação

A API está organizada nas seguintes tags:

### 🙋 Usuários
- **POST** `/usuario` - Cadastrar novo usuário (sem autenticação)
- **POST** `/usuario/login` - Autenticar usuário (sem autenticação)
- **GET** `/usuario/getByEmail` - Buscar usuário por email
- **GET** `/usuario/findAll` - Listar todos os usuários
- **PUT** `/usuario` - Atualizar dados do usuário
- **DELETE** `/usuario/deleteByEmail/{email}` - Deletar usuário

### 🏠 Endereços
- **POST** `/usuario/endereco` - Cadastrar novo endereço
- **PUT** `/usuario/endereco` - Atualizar endereço
- **DELETE** `/usuario/deleteByEndereco/{enderecoId}` - Deletar endereço

### 📞 Telefones
- **POST** `/usuario/telefone` - Cadastrar novo telefone
- **PUT** `/usuario/telefone` - Atualizar telefone
- **DELETE** `/usuario/deleteByTelefone/{telefoneId}` - Deletar telefone

## 🎨 Melhorias Implementadas

### Configuração Global
✅ Criada classe `OpenApiConfig` com:
- Informações detalhadas da API (título, versão, descrição)
- Informações de contato e licença
- Configuração de servidores (local e desenvolvimento)
- Esquema de segurança JWT documentado

### Anotações nos Endpoints
✅ Cada endpoint possui:
- `@Operation` - Título e descrição detalhada
- `@ApiResponses` - Documentação completa de todas as respostas possíveis
- `@Parameter` - Descrição de cada parâmetro com exemplos
- Códigos HTTP apropriados (200, 201, 400, 401, 404, 409)
- Exemplos de request/response

### Modelos de Dados (DTOs)
✅ Todos os DTOs documentados com:
- `@Schema` na classe - Descrição geral do modelo
- `@Schema` em cada campo - Descrição, exemplo e obrigatoriedade
- Especificação de campos somente leitura (READ_ONLY)
- Limites de tamanho quando aplicável

### Tags Organizacionais
✅ Endpoints agrupados por funcionalidade:
- **Usuários** - Operações principais de usuário
- **Endereços** - Gerenciamento de endereços
- **Telefones** - Gerenciamento de telefones

## 💡 Dicas de Uso

### Testando Fluxos Completos
1. **Criar e autenticar**
   - POST `/usuario` → POST `/usuario/login` → Authorize

2. **Gerenciar endereços**
   - POST `/usuario/endereco` → GET `/usuario/getByEmail` → PUT `/usuario/endereco`

3. **Gerenciar telefones**
   - POST `/usuario/telefone` → PUT `/usuario/telefone` → DELETE `/usuario/deleteByTelefone/{id}`

### Entendendo as Respostas de Erro
Todos os erros seguem o padrão `ErrorResponseDTO`:
```json
{
  "timestamp": "2026-06-28T14:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Usuário não encontrado",
  "path": "/usuario/getByEmail"
}
```

### Códigos HTTP Comuns
- **200 OK** - Requisição bem-sucedida
- **400 Bad Request** - Dados inválidos
- **401 Unauthorized** - Token inválido ou expirado
- **404 Not Found** - Recurso não encontrado
- **409 Conflict** - Conflito (ex: email já cadastrado)

## 🔧 Personalização

### Alterar Porta do Servidor
No arquivo `application.properties`:
```properties
server.port=8080
```

### Customizar Path do Swagger
Adicione no `application.properties`:
```properties
springdoc.swagger-ui.path=/api-docs
springdoc.api-docs.path=/v3/api-docs
```

## 📝 Padrões Seguidos

- ✅ OpenAPI 3.0
- ✅ RESTful best practices
- ✅ Documentação completa de todos os endpoints
- ✅ Exemplos realistas em todas as requisições
- ✅ Descrições claras e profissionais
- ✅ Organização por tags funcionais
- ✅ Segurança JWT documentada
- ✅ Tratamento de erros padronizado

## 🎯 Checklist de Qualidade

- [x] Todos os endpoints documentados
- [x] Todas as respostas HTTP documentadas
- [x] Todos os DTOs com @Schema
- [x] Exemplos em todos os campos
- [x] Tags organizadas por funcionalidade
- [x] Segurança JWT configurada
- [x] Erros padronizados e documentados
- [x] Descrições claras e profissionais
- [x] Informações de contato e licença

---

**Desenvolvido por:** Alexandre  
**Versão da API:** 1.0.0  
**Framework:** Spring Boot 3.5.10 + SpringDoc OpenAPI 2.8.0

