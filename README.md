# 🗂️ Gerenciador de Atividades — Backend

API REST desenvolvida em **Spring Boot + PostgreSQL** para gerenciar grupos e atividades, permitindo operações de criação, atualização, listagem e exclusão.  
Parte do projeto completo de um **Gerenciador de Atividades** com frontend em React + TypeScript.

---

## 🚀 Tecnologias Utilizadas

<div align="left">

![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.6-6DB33F?logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?logo=postgresql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?logo=apachemaven&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-✔️-CA4245?logo=java&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-✔️-2496ED?logo=docker&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-✔️-CC0200?logo=flyway&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI_3-85EA2D?logo=swagger&logoColor=black)

</div>

---

## 📘 Descrição do Projeto

O **Gerenciador de Atividades** é uma aplicação para organizar tarefas e agrupá-las em **grupos temáticos**.  
A API oferece endpoints para manipular **atividades** e **grupos**, servindo dados para o frontend (React).

A estrutura segue o padrão **Controller → Service → Repository**, com uso de **DTOs** para separação entre as camadas, garantindo clareza e manutenibilidade.

---

## 🏗️ Arquitetura

```
src/main/java/com/caiocesar/gerenciador_de_atividades/
├── config/           # Configurações (CORS, Swagger, tratamento de exceções)
├── controller/       # Endpoints REST
├── dto/              # Objetos de transferência de dados
│   ├── activity/
│   └── group/
├── exception/        # Exceções customizadas
├── infrastructure/
│   ├── entity/       # Entidades JPA
│   └── repository/   # Interfaces Spring Data JPA
└── service/          # Regras de negócio
```

---

## ✅ Diferenciais Técnicos

- **Docker + Docker Compose** — sobe toda a aplicação com um único comando
- **Flyway** — versionamento e controle de migrations do banco de dados
- **Swagger/OpenAPI 3** — documentação interativa dos endpoints
- **Tratamento global de exceções** — respostas padronizadas com status HTTP corretos
- **Testes unitários** — cobertura dos services com JUnit 5 e Mockito
- **DTOs específicos por operação** — separação clara entre entrada e saída de dados
- **CORS centralizado** — configuração em classe dedicada

---

## 🐳 Como Executar com Docker (recomendado)

### Pré-requisitos
- [Docker](https://www.docker.com/) instalado

### Passos

```bash
# 1. Clonar o repositório
git clone https://github.com/CaioMiranda12/api-gerenciador-de-atividades.git
cd api-gerenciador-de-atividades

# 2. Subir a aplicação
docker compose up --build
```

A API estará disponível em: **http://localhost:8080**

Para encerrar:
```bash
docker compose down
```

---

## 📄 Documentação Swagger

Com a aplicação rodando, acesse:

👉 **http://localhost:8080/swagger-ui/index.html**

---

## ⚙️ Endpoints

### 🔹 Grupos (`/groups`)

| Método | Endpoint | Descrição | Status |
|:-------|:---------|:----------|:-------|
| `GET` | `/groups` | Lista todos os grupos | 200 |
| `GET` | `/groups/{id}` | Busca grupo pelo ID | 200 / 404 |
| `POST` | `/groups` | Cria um novo grupo | 201 |
| `PUT` | `/groups/{id}` | Atualiza um grupo | 200 / 404 |
| `DELETE` | `/groups/{id}` | Remove um grupo | 204 / 404 |

### 🔹 Atividades (`/activities`)

| Método | Endpoint | Descrição | Status |
|:-------|:---------|:----------|:-------|
| `GET` | `/activities` | Lista todas as atividades | 200 |
| `POST` | `/activities` | Cria uma nova atividade | 201 / 404 |
| `PUT` | `/activities/{id}` | Atualiza uma atividade | 200 / 404 |
| `PUT` | `/activities/reorder` | Reordena atividades | 200 |
| `DELETE` | `/activities/{id}` | Remove uma atividade | 204 / 404 |

---

## 📦 Exemplos de Requisição

### Criar grupo
```http
POST /groups
Content-Type: application/json

{
  "name": "Estudos"
}
```

**Resposta (201):**
```json
{
  "id": 1,
  "name": "Estudos",
  "activities": []
}
```

---

### Criar atividade
```http
POST /activities
Content-Type: application/json

{
  "description": "Estudar Spring Boot",
  "dueDate": "2026-07-01",
  "groupId": 1
}
```

**Resposta (201):**
```json
{
  "id": 1,
  "description": "Estudar Spring Boot",
  "dueDate": "2026-07-01",
  "completed": false,
  "groupId": 1,
  "groupName": "Estudos",
  "position": 0
}
```

---

### Reordenar atividades
```http
PUT /activities/reorder
Content-Type: application/json

[
  { "id": 1, "groupId": 1, "position": 0 },
  { "id": 2, "groupId": 1, "position": 1 }
]
```

**Resposta (200):** sem corpo

---

## 🧩 DTOs Principais

### CreateGroupDTO
```json
{ "name": "string" }
```

### CreateActivityDTO
```json
{
  "description": "string",
  "dueDate": "yyyy-MM-dd",
  "groupId": 1
}
```

### ReorderActivityDTO
```json
{
  "id": 1,
  "groupId": 1,
  "position": 0
}
```

---

## 🧪 Testes

```bash
./mvnw test
```

Cobertura com **JUnit 5 + Mockito** nos services:
- `GroupServiceTest` — 5 testes
- `ActivityServiceTest` — 4 testes
