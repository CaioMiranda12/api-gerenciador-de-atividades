# 🗂️ Gerenciador de Atividades — Backend

API REST desenvolvida em **Spring Boot + PostgreSQL** para gerenciar grupos e atividades, permitindo operações de criação, atualização, listagem e exclusão.  
Parte do projeto completo de um **Gerenciador de Atividades** com frontend em React + TypeScript.

---

## 🚀 Tecnologias Utilizada

<div align="left">
  
![Java](https://img.shields.io/badge/Java-17-ED8B00?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.0-6DB33F?logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-4169E1?logo=postgresql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?logo=apachemaven&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-✔️-CA4245?logo=java&logoColor=white)

</div>

---

## 📘 Descrição do Projeto

O **Gerenciador de Atividades** é uma aplicação para organizar tarefas e agrupá-las em **grupos temáticos**.  
A API oferece endpoints para manipular **atividades** e **grupos**, servindo dados para o frontend (React).

A estrutura segue o padrão **Service + DTO + Controller**, garantindo separação de responsabilidades e clareza.

---

## ⚙️ Endpoints Principais

### 🔹 **Grupos (`/groups`)**
| Método | Endpoint | Descrição |
|:-------|:----------|:-----------|
| `GET` | `/groups` | Retorna todos os grupos |
| `GET` | `/groups/{id}` | Retorna um grupo pelo ID |
| `POST` | `/groups` | Cria um novo grupo (`CreateGroupDTO`) |
| `PUT` | `/groups/{id}` | Atualiza um grupo existente |
| `DELETE` | `/groups/{id}` | Exclui um grupo |

### 🔹 **Atividades (`/activities`)**
| Método | Endpoint | Descrição |
|:-------|:----------|:-----------|
| `GET` | `/activities` | Lista todas as atividades |
| `POST` | `/activities` | Cria uma nova atividade (`ActivityDTO`) |
| `PUT` | `/activities/{id}` | Atualiza uma atividade existente |
| `DELETE` | `/activities/{id}` | Remove uma atividade |

---

## 🧩 DTOs e Entidades

### **ActivityDTO**
```java
public class ActivityDTO {
    private Long id;
    private String description;
    private LocalDate dueDate;
    private boolean completed;
    private Long groupId;
    private String groupName;
    private Integer position;
}
```

### **GroupDTO**
```java
public class GroupDTO {
    private Long id;
    private String name;
    private List<ActivityDTO> activities;
}
```

### **CreateGroupDTO**
```java
public class CreateGroupDTO {
    private String name;
}

```

### **CreateActivityDTO**
```java
public class CreateActivityDTO {
    private String description;
    private LocalDate dueDate;
    private Long groupId;
}
```

