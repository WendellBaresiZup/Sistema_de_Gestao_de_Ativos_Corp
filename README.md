# Sistema de Gestão de Ativos Corporativos

[![Java](https://img.shields.io/badge/Java-17-blue.svg)](https://java.com)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.1-green.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-Build-red.svg)](https://maven.apache.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-DB-blue.svg)](https://www.postgresql.org/)
[![H2](https://img.shields.io/badge/H2-DB-lightgrey.svg)](https://www.h2database.com/)
[![AWS](https://img.shields.io/badge/AWS-Cloud-orange.svg)](https://aws.amazon.com)
[![EC2](https://img.shields.io/badge/AWS-EC2-orange.svg)](https://aws.amazon.com/ec2/)
[![RDS](https://img.shields.io/badge/AWS-RDS-blue.svg)](https://aws.amazon.com/rds/)
[![Lambda](https://img.shields.io/badge/AWS-Lambda-yellow.svg)](https://aws.amazon.com/lambda/)
[![API Gateway](https://img.shields.io/badge/AWS-API--Gateway-orange.svg)](https://aws.amazon.com/api-gateway/)
[![CloudWatch](https://img.shields.io/badge/AWS-CloudWatch-green.svg)](https://aws.amazon.com/cloudwatch/)
[![IAM](https://img.shields.io/badge/AWS-IAM-blue.svg)](https://aws.amazon.com/iam/)
[![Swagger](https://img.shields.io/badge/Swagger-UI-green.svg)](https://swagger.io/tools/swagger-ui/)
[![GitHub](https://img.shields.io/badge/GitHub-Repo-black.svg)](https://github.com/)
[![Docker](https://img.shields.io/badge/Docker-Container-blue.svg)](https://www.docker.com/)
[![JUnit](https://img.shields.io/badge/JUnit-Testing-red.svg)](https://junit.org/)
[![Git](https://img.shields.io/badge/Git-VersionControl-orange.svg)](https://git-scm.com/)
[![DrawIO](https://img.shields.io/badge/DrawIO-Diagram-blue.svg)](https://www.diagrams.net/)

[🔗 Documentação da API (Swagger)](http://localhost:8080/swagger-ui.html)

---

## Índice

1. [Tecnologias Utilizadas](#tecnologias-utilizadas)
2. [Funcionalidades Principais](#funcionalidades-principais)
3. [Regras de Negócio](#regras-de-negocio)
4. [Endpoints da API](#endpoints-da-api)
5. [Como Executar](#como-executar)
6. [Testes](#testes)
7. [Arquitetura AWS](#arquitetura-aws)
8. [Equipe](#equipe)

---

## Tecnologias Utilizadas


### Backend
- Java 17
- Spring Boot 3.1
- Maven

### Banco de Dados
- PostgreSQL (Produção)
- H2 (Testes)

### DevOps & Infra
- Docker
- Git
- GitHub Actions (CI/CD)

### Ferramentas de Testes
- JUnit
- Mockito

### Documentação
- Swagger (OpenAPI)

### AWS
- EC2 (Deploy)
- RDS (PostgreSQL)
- Lambda (Funções Serverless)
- API Gateway
- CloudWatch (Monitoramento)
- VPC (Rede Privada)
- IAM (Gestão de Acessos)
- SNS (Notificações)


---

## Funcionalidades Principais


- Cadastro de ativos com histórico técnico
- Associação dinâmica ativo-colaborador
- Alertas automáticos via AWS SNS
- Controle de acesso granular (IAM Roles)
- Monitoramento em tempo real (CloudWatch Logs)

---
## Regras de Negocio
___

#### Tabela de Relacionamentos

| Entidade      | Relacionamento         | Regra de Validação                |
|---------------|-----------------------|-----------------------------------|
| Active        | 1:N → AssetAssignment | Serial Number único               |
| User          | 1:N → AssetAssignment | Papéis: ADMIN ou USER             |
| MovementLog   | N:1 → Active          | Registro imutável                 |



### Validações Críticas

```json

{
"Active": {
"warrantyExpires": "DEVE ser posterior à acquisitionDate",
"serialNumber": "DEVE ser único"
},
"User": {
"email": "Formato válido obrigatório",
"role": "ENUM(ADMIN, USER)"
}
}
```
----

## Endpoints da API

### **1. Ativos** (`/api/actives`)
| Método | Endpoint                  | Descrição                                  |
|--------|---------------------------|--------------------------------------------|
| POST   | `/api/actives`            | Cria novo ativo                            |
| GET    | `/api/actives/{id}`       | Busca ativo por ID                         |
| GET    | `/api/actives`            | Lista todos os ativos                      |
| GET    | `/api/actives/available`  | Lista ativos disponíveis                   |
| DELETE | `/api/actives/{id}`       | Remove ativo                               |

---

### **2. Associações** (`/api/assignments`)
| Método | Endpoint                          | Descrição                              |
|--------|-----------------------------------|----------------------------------------|
| POST   | `/api/assignments/associate`      | Associa ativo a usuário                |
| DELETE | `/api/assignments/{id}`           | Remove associação                      |
| GET    | `/api/assignments/{id}`           | Busca associação por ID                |
| GET    | `/api/assignments/active`         | Lista associações ativas               |
| GET    | `/api/assignments/user/{userId}`  | Lista associações por usuário          |

---

### **3. Logs de Movimentação** (`/api/movement-logs`)
| Método | Endpoint  | Descrição               |
|--------|-----------|-------------------------|
| POST   | `/`       | Registra movimentação   |

---

### **4. Usuários** (`/api/users`)
| Método | Endpoint                  | Descrição                      |
|--------|---------------------------|--------------------------------|
| POST   | `/api/users/register`     | Cadastra novo usuário          |
| POST   | `/api/users/login`        | Login de usuário               |
| GET    | `/api/users/{id}`         | Busca usuário por ID           |
| GET    | `/api/users/search/name`  | Busca usuários por nome        |
---

## Exemplos de Requisicoes

### **POST /api/actives** <a id="post-actives"></a>
**Request**:
```json
{
  "type": "Notebook",
  "model": "Dell Inspiron 15",
  "serialNumber": "ABC1234567",
  "acquisitionDate": "2024-01-15",
  "warrantyExpires": "2026-01-15"
}
```
Response (201):

```json
{
  "id": 1,
  "type": "Notebook",
  "serialNumber": "ABC1234567",
  "available": true
}
```
POST /api/assignments/associate <a id="post-assignments-associate"></a>
Request:

```json
{
  "userId": 1,
  "activeId": 2
}
```

Response (201):

```json
{
  "id": 5,
  "activeId": 2,
  "activeSerialNumber": "ABC1234567",
  "userId": 1,
  "userName": "João Silva",
  "assignedAt": "2024-05-21T10:00:00"
}
```

POST /api/movement-logs <a id="post-movement-logs"></a>
Request (Modelo da Entidade):

```json
{
  "activeId": 2,
  "userId": 1,
  "action": "ASSOCIACAO"
}
```

Response (200):

```json
{
  "id": 10,
  "movementDate": "2024-05-21T10:00:00",
  "action": "ASSOCIACAO"
}
```
POST /api/users/register <a id="post-users-register"></a>
Request:

```json
{
  "nome": "Pedro Mota",
  "email": "pedro@email.com",
  "telefone": "22998877665",
  "senha": "senha123",
  "role": "USER"
}
```
Response (201):

```json
{
  "id": 3,
  "nome": "Pedro Mota",
  "email": "pedro@email.com",
  "role": "USER"
}
```
GET /api/actives/available <a id="get-actives-available"></a>
Response (200):

```json
[
  {
    "id": 2,
    "type": "Software",
    "serialNumber": "LIC-ADOBE-123",
    "available": true
  }
]
```
GET /api/assignments/active <a id="get-assignments-active"></a>
Response (200):

```json
[
  {
    "id": 5,
    "activeType": "Notebook",
    "userName": "João Silva",
    "assignedAt": "2024-05-21T10:00:00"
  }
]
```


Todos os endpoints estão documentados no Swagger UI:
http://localhost:8080/swagger-ui.html
---


## Como Executar

##### Clone o repositório
git clone https://github.com/WendellBaresiZup/Sistema_de_Gestao_de_Ativos_Corp.git

##### Configure as variáveis AWS
export AWS_ACCESS_KEY_ID="SUA_CHAVE_AWS"
export AWS_SECRET_ACCESS_KEY="SUA_SECRETA_AWS"

#### Execute com Maven
mvn spring-boot:run -Dspring.profiles.active=prod


#### Variaveis de ambiente para banco de dados
export DB_HOST="localhost"
export DB_PORT="5432"
export DB_NAME="ativos"
export DB_USER="usuario"
export DB_PASSWORD="senha"

----
## Testes



```
mvn test
```

----

## Arquitetura AWS

### Fluxo de Trabalho

![Image](https://github.com/user-attachments/assets/7637f53a-ee4a-4bd7-81f2-20aac350de11)

```plaintext
1. Usuário → API Gateway (HTTPS/Rate Limiting)
2. API Gateway → EC2 (Spring Boot App)
3. EC2 → RDS PostgreSQL (VPC Privada)
4. Eventos → Lambda (Processa alertas)
5. Lambda → SNS (Envia notificações)
6. CloudWatch → Monitora todos os serviços
```

---

## Equipe

### **Squad 1**

| **Função**           | **Membro**            |
|----------------------|-----------------------|
| **Desenvolvedores**  | Gabrielly Rossi       |
|                      | João Brito            |
|                      | Wendell Baresi        |
| **Product Owner**    | Renato Ramon          |
| **Scrum Master**     | Laura Fumagalli       |