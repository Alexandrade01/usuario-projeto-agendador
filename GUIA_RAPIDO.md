# 🎯 GUIA RÁPIDO - Documentação Swagger

## ⚡ Início Rápido (3 passos)

### 1. Inicie a aplicação
```powershell
.\iniciar-swagger.ps1
```
**OU** manualmente:
```powershell
.\gradlew bootRun
```

### 2. Acesse o Swagger
```
http://localhost:8080/swagger-ui/index.html
```

### 3. Teste a API
1. POST `/usuario` - Criar usuário
2. POST `/usuario/login` - Obter token
3. Click "Authorize" 🔒 - Colar token
4. Testar qualquer endpoint!

---

## 📸 Visual da Documentação

### Página Principal
```
┌─────────────────────────────────────────────────────────────┐
│  API de Gerenciamento de Usuários - Projeto Agendador      │
│  v1.0.0                                                     │
│                                                             │
│  API REST para gerenciamento completo de usuários...       │
│  [Ver mais detalhes]                                        │
│                                                             │
│  Servers: ▼ Local - http://localhost:8080                  │
│                                                             │
│  [Authorize 🔒]                                             │
└─────────────────────────────────────────────────────────────┘
```

### Endpoints Organizados
```
▼ Usuários (7 endpoints) ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

  POST   /usuario               Cadastrar novo usuário ⭐
  POST   /usuario/login         Autenticar usuário ⭐
  GET    /usuario/getByEmail    Buscar usuário por email 🔒
  GET    /usuario/findAll       Listar todos os usuários 🔒
  PUT    /usuario               Atualizar dados 🔒
  DELETE /usuario/deleteByEmail/{email}  Deletar usuário 🔒

▼ Endereços (3 endpoints) ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

  POST   /usuario/endereco      Cadastrar novo endereço 🔒
  PUT    /usuario/endereco      Atualizar endereço 🔒
  DELETE /usuario/deleteByEndereco/{id}  Deletar endereço 🔒

▼ Telefones (3 endpoints) ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

  POST   /usuario/telefone      Cadastrar novo telefone 🔒
  PUT    /usuario/telefone      Atualizar telefone 🔒
  DELETE /usuario/deleteByTelefone/{id}  Deletar telefone 🔒

⭐ = Sem autenticação  |  🔒 = Requer autenticação JWT
```

### Exemplo de Endpoint Expandido
```
▼ POST /usuario                                    [Try it out]
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

📝 Cadastrar novo usuário
   Cria um novo usuário no sistema com os dados fornecidos.
   Este endpoint não requer autenticação.

PARAMETERS
   
REQUEST BODY (application/json) *required
┌─────────────────────────────────────────────────────┐
│  {                                                  │
│    "nome": "João da Silva",                         │
│    "email": "joao.silva@email.com",                 │
│    "senha": "senha123"                              │
│  }                                                  │
└─────────────────────────────────────────────────────┘

RESPONSES

✅ 200 - Usuário cadastrado com sucesso
   Media type: application/json
   Example Value | Schema
   {
     "nome": "João da Silva",
     "email": "joao.silva@email.com",
     "enderecos": [...],
     "telefones": [...]
   }

❌ 409 - Email já cadastrado no sistema
   Media type: application/json
   {
     "timestamp": "2026-06-28T14:30:00",
     "status": 409,
     "error": "Conflict",
     "message": "Email já cadastrado",
     "path": "/usuario"
   }

❌ 400 - Dados inválidos fornecidos
   Media type: application/json
   ErrorResponseDTO

[Execute]  [Clear]
```

---

## 🔐 Fluxo de Autenticação Visual

```
┌──────────────┐
│ 1. Criar     │
│    Usuário   │
│              │
│ POST /usuario│────┐
└──────────────┘    │
                    ↓
              ✅ Criado!
                    │
┌──────────────┐    │
│ 2. Fazer     │    │
│    Login     │←───┘
│              │
│ POST /login  │────┐
└──────────────┘    │
                    ↓
          📋 Token JWT Gerado
          "Bearer eyJhbGc..."
                    │
┌──────────────┐    │
│ 3. Autorizar │    │
│    (Swagger) │←───┘
│              │
│ 🔒 Button    │────┐
└──────────────┘    │
                    ↓
    🎉 Todos os endpoints
       protegidos liberados!
                    │
┌──────────────┐    │
│ 4. Usar API  │←───┘
│              │
│ GET, PUT,    │
│ DELETE, etc. │
└──────────────┘
```

---

## 📋 Modelos de Dados Documentados

### UsuarioDTO
```json
{
  "nome": "string",      // Nome completo do usuário ✅ Obrigatório
  "email": "string",     // Email (usado como login) ✅ Obrigatório
  "senha": "string",     // Senha (min. 6 caracteres) ✅ Obrigatório
  "enderecos": [...],    // Lista de endereços
  "telefones": [...]     // Lista de telefones
}
```

### EnderecoDTO
```json
{
  "id": 1,               // ID único (somente leitura)
  "rua": "string",       // Nome da rua ✅ Obrigatório
  "numero": 123,         // Número do imóvel ✅ Obrigatório
  "complemento": "string", // Complemento
  "bairro": "string",    // Bairro ✅ Obrigatório
  "cidade": "string",    // Cidade ✅ Obrigatório
  "estado": "string",    // Estado (UF) ✅ Obrigatório
  "cep": "string"        // CEP ✅ Obrigatório
}
```

### TelefoneDTO
```json
{
  "id": 1,               // ID único (somente leitura)
  "numero": "987654321", // Número (sem DDD) ✅ Obrigatório
  "ddd": "11"            // DDD ✅ Obrigatório
}
```

### ErrorResponseDTO
```json
{
  "timestamp": "2026-06-28T14:30:00", // Data/hora do erro
  "status": 404,                       // Código HTTP
  "error": "Not Found",                // Tipo do erro
  "message": "Usuário não encontrado", // Mensagem
  "path": "/usuario/getByEmail"        // Caminho da requisição
}
```

---

## 🎨 Códigos de Resposta HTTP

| Código | Significado | Quando Ocorre |
|--------|-------------|---------------|
| 🟢 200 | OK | Operação bem-sucedida |
| 🔴 400 | Bad Request | Dados inválidos ou malformados |
| 🟠 401 | Unauthorized | Token ausente, inválido ou expirado |
| 🔴 404 | Not Found | Recurso não encontrado |
| 🟣 409 | Conflict | Email já cadastrado |

---

## 💡 Dicas de Uso

### ✅ DOs (Faça)
- Use o botão "Try it out" para testar
- Leia as descrições de cada endpoint
- Verifique os exemplos fornecidos
- Copie o token completo (com "Bearer")
- Use os schemas como referência

### ❌ DON'Ts (Não faça)
- Não esqueça de autorizar antes de testar endpoints protegidos
- Não cole apenas o token (inclua "Bearer ")
- Não ignore as respostas de erro
- Não use endpoints protegidos sem autenticar

---

## 🔍 Funcionalidades do Swagger UI

### Testando Endpoints
1. **Expandir endpoint** - Click no método
2. **Try it out** - Habilitar edição
3. **Editar parâmetros** - Preencher dados
4. **Execute** - Executar requisição
5. **Ver resposta** - Analisar resultado

### Explorando Schemas
- Click em "Schema" nos responses
- Veja todos os campos disponíveis
- Entenda tipos de dados
- Identifique campos obrigatórios

### Copiando cURL
- Após executar, role para baixo
- Encontre "Curl" tab
- Copie o comando completo
- Use em terminal ou scripts

### Exportando Spec
- `/v3/api-docs` - JSON format
- `/v3/api-docs.yaml` - YAML format
- Use em ferramentas de API

---

## 📚 Documentação Detalhada

Para informações completas, consulte:

| Arquivo | Conteúdo |
|---------|----------|
| `SWAGGER_DOCUMENTATION.md` | 📖 Guia completo de uso |
| `MELHORIAS_SWAGGER.md` | 🔧 Detalhes técnicos |
| `RESUMO_EXECUTIVO.md` | 📊 Visão geral executiva |
| `GUIA_RAPIDO.md` | ⚡ Este guia visual |

---

## 🎯 Checklist de Uso

Antes de começar:
- [ ] Aplicação rodando
- [ ] Swagger acessível
- [ ] Usuário criado
- [ ] Login feito
- [ ] Token copiado
- [ ] Autorização configurada
- [ ] Pronto para testar! 🚀

---

## 🆘 Problemas Comuns

### Porta em uso?
```powershell
# Ver o que está usando a porta 8080
Get-NetTCPConnection -LocalPort 8080
```

### Banco de dados não conecta?
- Verifique `application.properties`
- Confirme que o PostgreSQL está rodando
- Valide credenciais de banco

### Token expirado?
- Faça login novamente
- Obtenha novo token
- Atualize autorização

---

**🚀 Pronto para começar? Execute: `.\iniciar-swagger.ps1`**

**🌐 Ou acesse diretamente: http://localhost:8080/swagger-ui/index.html**

