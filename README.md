# 🛰️ OrbitAlert — Java Advanced API

> **FIAP Global Solution 2026/1 · Turma 2TDS Fevereiro · Tema: Economia Espacial**

---

## 📋 Descrição

O **OrbitAlert** é uma plataforma de alertas precoces de desastres naturais que combina dados do satélite Sentinel-1 (ESA/Copernicus) com inteligência artificial generativa para antecipar eventos de deslizamento e enchente com até 48 horas de antecedência.

Esta API Java Spring Boot é o **hub central** da solução — consome dados orbitais, calcula o índice de risco, aciona a Claude API para análise em linguagem natural, gerencia o ciclo de vida dos alertas e serve o aplicativo mobile e as estações IoT de campo.

---

## 👥 Equipe

| Nome | RM |
|---|---|
| Gabriel Sbrana Campos | RM 565849 |
| Moisés Waidemann | RM 563719 |
| Thiago Rodrigues da Mota | RM 563650 |
| Richard Freitas | RM 566127 |

---

## 🔗 Links

| Recurso | Link |
|---|---|
| 🚀 **Deploy (Railway)** | `https://orbitalert.up.railway.app` |
| 📄 **Swagger / OpenAPI** | `https://orbitalert.up.railway.app/swagger` |
| 🎥 **Vídeo de Apresentação** | *(link após gravação)* |
| 🎬 **Vídeo Pitch** | *(link após gravação)* |
| 📦 **GitHub** | `https://github.com/orbitalert-gs/orbitalert-java` |

---

## 🏗️ Arquitetura

```
React Native (App Mobile)
        │ HTTPS/JWT
        ▼
Java Spring Boot (API Central)  ←── ESP32 (MQTT/HiveMQ)
        │              │
        ▼              ▼
   Oracle DB     Claude API (IA)
        │
        └── ASP.NET Core (mesmo banco)
```

### Stack

| Camada | Tecnologia |
|---|---|
| Backend | Java 21 + Spring Boot 4 |
| ORM | Spring Data JPA + Hibernate |
| Banco de Dados | Oracle DB (FIAP) + PostgreSQL (Docker) |
| Segurança | Spring Security + JWT (jjwt 0.12.6) |
| Documentação | SpringDoc OpenAPI 3 / Swagger |
| Cache | Spring Cache (Simple) |
| Mapeamento | MapStruct 1.6.3 |
| IA Generativa | Claude API (Anthropic) |
| IoT | ESP32 + MQTT TLS + HiveMQ Cloud |
| Deploy | Railway + Docker |

---

## 📦 Estrutura de Pacotes

```
br.com.fiap.orbitAlert
├── config/          → SwaggerConfig, CorsConfig, GlobalExceptionHandler, Exceptions
├── control/         → REST Controllers (11) + AutenticacaoController
├── dto/             → DTOs com Bean Validation (11)
├── mapper/          → MapStruct Mappers (11)
├── model/           → Entidades JPA (12) + Enums (3) + Records (4)
│   ├── enums/
└── records/
├── repository/      → JPA Repositories (11)
├── security/        → JWTUtil, JWTAuthFilter, SegurancaConfig, UsuarioConfig, AuthManager
└── service/         → CachingServices (10) + PaginacaoServices (10)
```

---

## 🗄️ Banco de Dados

O projeto utiliza Oracle DB com tabelas criadas via DDL. As entidades JPA apenas mapeam as tabelas existentes — **não recria o banco** (`ddl-auto=none`).

### Tabelas Mapeadas

```
TB_LOG_ERRO            TB_USUARIO
TB_MUNICIPIO           TB_USUARIO_MUNICIPIO
TB_ZONA_RISCO          TB_ESTACAO_IOT
TB_LEITURA_IOT         TB_TIPO_ALERTA
TB_ALERTA              TB_ANALISE_IA
TB_HISTORICO_ALERTA    TB_NOTIFICACAO
```

### Modelagem Avançada

| Recurso | Implementação |
|---|---|
| Herança | `BaseEntity` com `DT_CADASTRO` — `Usuario`, `Municipio`, `ZonaRisco` estendem |
| Chave composta | `UsuarioMunicipioPK` (`@Embeddable`) em `UsuarioMunicipio` (`@EmbeddedId`) |
| Relacionamentos | `@OneToMany`, `@ManyToOne`, `@OneToOne` (AnaliseIa ↔ Alerta) |
| Java Records | `AlertaResumoRecord`, `LeituraIotRecord`, `ErroResponseRecord`, `MunicipioRiscoRecord` |

---

## 🔐 Autenticação

A API usa **Spring Security + JWT**. Endpoints protegidos exigem o token no header:

```
Authorization: Bearer <token>
```

### Fluxo

```
1. POST /usuario/novo         → cadastra usuário (senha encodada com BCrypt)
2. POST /autenticacao/login   → retorna JWT token
3. Qualquer endpoint          → Authorization: Bearer <token>
```

### Endpoints públicos (sem token)

```
POST /autenticacao/login
GET  /swagger-ui/**
GET  /v3/**
GET  /swagger/**
```

---

## 🚀 Como Rodar

### Pré-requisitos

- Java 21+
- Maven 3.8+
- IntelliJ IDEA (recomendado)
- Acesso ao Oracle FIAP (`oracle.fiap.com.br:1521:ORCL`)

### Passos

**1. Clone o repositório**
```bash
git clone https://github.com/orbitalert-gs/orbitalert-java.git
cd orbitalert-java
```

**2. Configure o `application.properties`**
```properties
spring.datasource.username=SEU_RM
spring.datasource.password=SUA_SENHA_FIAP
```

**3. Execute os scripts SQL** (na ordem)
```
01_create_tables.sql
02_procedures_carga.sql
03_carga_dados.sql
```

**4. Rode a aplicação**
```bash
mvn spring-boot:run
```

**5. Acesse o Swagger**
```
http://localhost:8080/swagger
```

---

## 📡 Endpoints

| Recurso | Base URL | Endpoints especiais |
|---|---|---|
| Autenticação | `/autenticacao` | `POST /login` |
| Usuários | `/usuario` | `/perfil`, `/municipio/{id}` |
| Municípios | `/municipio` | `/dashboard`, `/com-alertas` |
| Vínculos Usuário-Município | `/usuario-municipio` | `/usuario/{id}`, `/municipio/{id}` |
| Zonas de Risco | `/zona-risco` | — |
| Estações IoT | `/estacao-iot` | — |
| Leituras IoT | `/leitura-iot` | `POST /mqtt` (payload ESP32) |
| Tipos de Alerta | `/tipo-alerta` | — |
| Alertas | `/alerta` | `/resumo`, `/status`, `/municipio/{id}`, `/criticos` |
| Análises IA | `/analise-ia` | — |
| Histórico de Alertas | `/historico-alerta` | — |
| Notificações | `/notificacao` | — |

Cada recurso expõe o padrão REST completo:

```
GET    /todos           → lista todos
GET    /paginar         → lista paginada (?page=0&size=5)
GET    /{id}            → busca por ID com HATEOAS
POST   /novo            → cria novo registro
PUT    /atualizar/{id}  → atualiza registro
DELETE /remover/{id}    → remove registro
```

---

## ✅ Requisitos Técnicos Atendidos

- [x] API REST com verbos HTTP corretos e HTTP Status Codes (200, 201, 204, 400, 404, 422, 500)
- [x] HATEOAS nos endpoints `GET /{id}` (`selfLink` + `todosLink`)
- [x] Entidades JPA com relacionamentos (`@OneToMany`, `@ManyToOne`, `@OneToOne`, `@ManyToMany`)
- [x] Modelagem avançada: herança (`BaseEntity`), chave composta (`@EmbeddedId`), `@Embeddable`
- [x] Bean Validation nos DTOs (`@NotBlank`, `@Email`, `@Min`, `@Max`, `@DecimalMin`, `@DecimalMax`)
- [x] Java Records para transferência de dados (`AlertaResumoRecord`, `LeituraIotRecord`, `MunicipioRiscoRecord`, `ErroResponseRecord`)
- [x] DTOs separados das entidades com MapStruct (`@Mapper`)
- [x] Paginação com `PageRequest` nos endpoints `/paginar`
- [x] Cache nas consultas frequentes (`@Cacheable`, `@CacheEvict`)
- [x] Tratamento global de exceções (`@RestControllerAdvice`) com `ErroResponseRecord`
- [x] Spring Security + JWT — endpoints protegidos, sessão stateless
- [x] Documentação Swagger/OpenAPI com autenticação Bearer JWT
- [x] CORS configurado para aceitar requisições do app mobile
- [x] JPQL e Native Queries nos Repositories
- [x] Deploy em nuvem (Railway)

---

## 🔗 Repositórios do projeto

| Disciplina | Repositório | Status |
|---|---|---|
| Java Advanced | orbitalert-java ← você está aqui | ✅ |
| .NET | [orbitalert-dotnet](#) | 🔧 |
| Banco de Dados | [orbitalert-database](#) | ✅ |
| Mobile | [orbitalert-mobile](#) | 🔧 |
| IoT | [orbitalert-iot](#) | 🔧 |
| DevOps | [orbitalert-devops](#) | 🔧 |
| Compliance & QA | [orbitalert-qa](#) | ✅ |

---

## 📚 Disciplina

**Java Advanced** — FIAP 2026 · 1º Semestre · Turma 2TDS Fevereiro
Global Solution 2026/1 — Tema: **Economia Espacial**
