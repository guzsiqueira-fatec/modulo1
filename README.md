# FATEC Módulo 1 - Person Management API

Sistema de gerenciamento de pessoas desenvolvido com Spring Boot, implementando arquitetura hexagonal (ports and adapters) com integração Kafka para eventos de domínio.

## 📋 Descrição do Projeto

Este projeto é uma API REST para gerenciamento de pessoas (CRUD completo), desenvolvida seguindo princípios de Clean Architecture e Domain-Driven Design (DDD). O sistema emite eventos de domínio para um broker Kafka sempre que operações de criação, atualização ou exclusão são realizadas, permitindo integração assíncrona com outros sistemas.

### Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.7**
- **PostgreSQL** - Banco de dados principal
- **Apache Kafka** - Message broker para eventos
- **JPA/Hibernate** - Persistência de dados
- **Maven** - Gerenciamento de dependências
- **Docker Compose** - Orquestração de containers

### Infraestrutura Adicional

- **MongoDB** - Suporte para logs (Graylog)
- **OpenSearch** - Motor de busca para logs
- **Graylog** - Centralização e visualização de logs
- **Kafka UI** - Interface web para gerenciamento do Kafka

## 🏗️ Arquitetura

O projeto segue a **Arquitetura Hexagonal (Ports and Adapters)** organizada em 4 módulos Maven:

### 1. **Domain** (`domain/`)
Camada de domínio puro, contendo as regras de negócio e entidades do sistema.

- **Entidades**: `Person` (com Value Objects `Name` e `BirthDate`)
- **Eventos de Domínio**: 
  - `PersonCreatedEvent`
  - `PersonUpdatedEvent`
  - `PersonDeletedEvent`
- **Exceções**: `PersonNotFoundException`
- **Sem dependências externas** (Spring, frameworks, etc.)

### 2. **Application** (`application/`)
Camada de casos de uso (use cases), implementando a lógica de aplicação.

- **Ports** (interfaces):
  - `PersonRepository` - Contrato para persistência
  - `EventEmitter` - Contrato para emissão de eventos
- **Use Cases**:
  - `CreatePersonUseCase` - Criação de pessoa
  - `FindPersonByIdUseCase` - Busca por ID
  - `FindAllPagedUseCase` - Listagem paginada
  - `UpdatePersonUseCase` - Atualização de pessoa
  - `DeletePersonUseCase` - Exclusão de pessoa (soft delete)
- **Paginação**: Implementação genérica com `Page` e `PageRequest`

### 3. **Infra** (`infra/`)
Camada de infraestrutura, implementando os adapters para tecnologias externas.

- **Persistência JPA**: 
  - `PersonJpa` - Entidade JPA
  - `PersonRepositoryJpa` - Interface Spring Data JPA
  - `PersonRepositoryImpl` - Implementação do port
- **Eventos Kafka**:
  - `EventEmitterKafka` - Publicador de eventos no Kafka
  - `EventEmitterLog` - Logger de eventos
  - `EventEmitterComposite` - Composite pattern para múltiplos emitters
  - `EventMapper` - Conversão de eventos de domínio para DTOs de integração
  - `TopicResolver` - Resolução de tópicos Kafka por tipo de evento

### 4. **Rest** (`rest/`)
Camada de apresentação, expondo a API REST.

- **Controllers**: `PersonController`
- **DTOs**: 
  - `CreatePersonDto`
  - `UpdatePersonDto`
  - `PersonDto`
- **Configurações**:
  - `KafkaConfig` - Configuração do Kafka producer
  - `PersonUseCaseConfig` - Injeção de dependências dos use cases

### Decisões Arquiteturais

#### Por que Arquitetura Hexagonal?
- ✅ **Desacoplamento**: O domínio não conhece frameworks ou tecnologias externas
- ✅ **Testabilidade**: Fácil criar testes unitários sem dependências externas
- ✅ **Flexibilidade**: Trocar implementações (ex: PostgreSQL → MongoDB) sem alterar lógica de negócio
- ✅ **Manutenibilidade**: Separação clara de responsabilidades

#### Padrões Implementados
- **Ports and Adapters**: Interfaces definem contratos, implementações são injetadas
- **Use Case Pattern**: Cada operação é um caso de uso independente
- **Value Objects**: `Name` e `BirthDate` encapsulam validações
- **Domain Events**: Eventos representam mudanças de estado no domínio
- **Repository Pattern**: Abstração de persistência
- **Composite Pattern**: Múltiplos emissores de eventos (Kafka + Log)
- **Soft Delete**: Exclusão lógica ao invés de física (`active` flag)

## 🚀 Rotas da API

**Base URL**: `http://localhost:8080/api/v1/persons`

### 1. Criar Pessoa
```http
POST /api/v1/persons
Content-Type: application/json

{
  "name": "João Silva",
  "birthDate": "1990-05-15"
}
```

**Response** (201 Created):
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "João Silva",
  "birthDate": "1990-05-15"
}
```

### 2. Buscar Pessoa por ID
```http
GET /api/v1/persons/{id}
```

**Response** (200 OK):
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "João Silva",
  "birthDate": "1990-05-15"
}
```

**Response** (404 Not Found):
```json
{
  "error": "Person not found"
}
```

### 3. Listar Pessoas (Paginado)
```http
GET /api/v1/persons?page=0&size=10
```

**Query Parameters**:
- `page` (opcional, padrão: 0) - Número da página
- `size` (opcional, padrão: 10) - Tamanho da página

**Response** (200 OK):
```json
{
  "items": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "name": "João Silva",
      "birthDate": "1990-05-15"
    }
  ],
  "currentPage": 0,
  "totalPages": 5,
  "totalItems": 50
}
```

### 4. Atualizar Pessoa
```http
PUT /api/v1/persons/{id}
Content-Type: application/json

{
  "name": "João Silva Santos",
  "birthDate": "1990-05-15"
}
```

**Response** (200 OK):
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "João Silva Santos",
  "birthDate": "1990-05-15"
}
```

### 5. Excluir Pessoa
```http
DELETE /api/v1/persons/{id}
```

**Response** (204 No Content)

## 📨 Mensagens Kafka (Eventos de Integração)

O sistema emite eventos para o Kafka em todas as operações de escrita. Estes eventos podem ser consumidos por outros sistemas para integração assíncrona.

### Configuração Kafka
- **Bootstrap Server**: `localhost:9092`
- **Client ID**: `person-service-producer`
- **Serialização**: JSON

### Tópicos e Mensagens

#### 1. Tópico: `person.created`
Emitido quando uma nova pessoa é criada.

**Estrutura da Mensagem**:
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "João Silva",
  "birthDate": "1990-05-15"
}
```

**Campos**:
- `id` (String): UUID da pessoa criada
- `name` (String): Nome completo
- `birthDate` (String): Data de nascimento no formato ISO-8601

---

#### 2. Tópico: `person.updated`
Emitido quando uma pessoa é atualizada.

**Estrutura da Mensagem**:
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "João Silva Santos",
  "birthDate": "1990-05-15"
}
```

**Campos**:
- `id` (String): UUID da pessoa atualizada
- `name` (String): Nome atualizado
- `birthDate` (String): Data de nascimento atualizada

---

#### 3. Tópico: `person.deleted`
Emitido quando uma pessoa é excluída (soft delete).

**Estrutura da Mensagem**:
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000"
}
```

**Campos**:
- `id` (String): UUID da pessoa excluída

---

### Exemplo de Consumidor (Java)

```java
@KafkaListener(topics = "person.created", groupId = "meu-consumidor")
public void handlePersonCreated(PersonCreatedEventDto event) {
    System.out.println("Nova pessoa criada: " + event.name());
    // Lógica de integração aqui
}
```

### Monitoramento de Mensagens
Acesse o **Kafka UI** em: `http://localhost:4000`

## 🐳 Instalação e Execução

### Pré-requisitos
- Java 21+
- Maven 3.8+
- Docker & Docker Compose

### 1. Subir a Infraestrutura
```bash
docker-compose up -d
```

Isso iniciará:
- PostgreSQL (porta 5432)
- Kafka (porta 9092)
- Kafka UI (porta 4000)
- MongoDB (porta 27017)
- OpenSearch
- Graylog (porta 9000)

### 2. Compilar o Projeto
```bash
mvn clean install
```

### 3. Executar a Aplicação
```bash
cd rest
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

### 4. Verificar o Banco de Dados
```bash
docker exec -it postgres_mod1 psql -U root -d MOD1
```

### 5. Acessar Ferramentas

- **Kafka UI**: http://localhost:4000
- **Graylog**: http://localhost:9000 (admin/admin)
- **API Health**: http://localhost:8080/actuator/health (se configurado)

## 📊 Estrutura do Banco de Dados

### Tabela: `person`
```sql
CREATE TABLE person (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    active BOOLEAN DEFAULT TRUE
);
```

## 🧪 Testando a API

### Criar uma Pessoa
```bash
curl -X POST http://localhost:8080/api/v1/persons \
  -H "Content-Type: application/json" \
  -d '{"name": "João Silva", "birthDate": "1990-05-15"}'
```

### Listar Pessoas
```bash
curl http://localhost:8080/api/v1/persons?page=0&size=10
```

### Buscar por ID
```bash
curl http://localhost:8080/api/v1/persons/{id}
```

### Atualizar Pessoa
```bash
curl -X PUT http://localhost:8080/api/v1/persons/{id} \
  -H "Content-Type: application/json" \
  -d '{"name": "João Silva Santos", "birthDate": "1990-05-15"}'
```

### Excluir Pessoa
```bash
curl -X DELETE http://localhost:8080/api/v1/persons/{id}
```

## 📝 Credenciais Padrão

### PostgreSQL
- **Host**: localhost:5432
- **Database**: MOD1
- **User**: root
- **Password**: root

### Graylog
- **URL**: http://localhost:9000
- **User**: admin
- **Password**: admin

## 📦 Módulos Maven

```
fatecpm_1 (parent)
├── domain (entidades e regras de negócio)
├── application (casos de uso e ports)
├── infra (implementação de adapters)
└── rest (API REST e configurações)
```

## 🔄 Fluxo de uma Requisição

1. **Controller** recebe requisição HTTP
2. **DTO** é validado e convertido para input do use case
3. **Use Case** executa lógica de negócio usando os ports
4. **Repository** persiste/busca dados no PostgreSQL
5. **Event Emitter** publica evento no Kafka
6. **Response** é construído e retornado ao cliente

## 🎯 Próximos Passos / Melhorias Futuras

- [ ] Implementar autenticação e autorização (Spring Security)
- [ ] Adicionar testes unitários e de integração
- [ ] Implementar cache com Redis
- [ ] Adicionar documentação Swagger/OpenAPI
- [ ] Implementar circuit breaker (Resilience4j)
- [ ] Adicionar métricas (Prometheus + Grafana)
- [ ] Implementar versionamento de API
- [ ] Criar consumidores de exemplo para os eventos Kafka

## 👥 Autor

Projeto desenvolvido para o curso da FATEC - Módulo 1

## 📄 Licença

Este projeto é apenas para fins educacionais.
