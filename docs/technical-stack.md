# Stack Tecnológica - Serviços DEM e MDM

## Visão Geral

O projeto utiliza uma stack moderna de tecnologias para implementar uma arquitetura de microserviços, com foco em escalabilidade, manutenibilidade e boas práticas de desenvolvimento.

## Tecnologias Principais

### 1. Linguagem e Framework
- **Java 17**
  - Versão LTS (Long Term Support)
  - Recursos modernos como Records, Pattern Matching, etc.
  - Suporte a módulos e melhorias de performance

- **Spring Boot 3.5.0**
  - Framework para desenvolvimento rápido de aplicações Java
  - Autoconfiguração e starters para diferentes necessidades
  - Suporte a desenvolvimento de microserviços

### 2. Persistência de Dados
- **PostgreSQL 15**
  - Banco de dados relacional robusto e confiável
  - Suporte a JSON e tipos de dados avançados
  - Recursos de concorrência e ACID

- **Spring Data JPA**
  - Abstração para acesso a dados
  - Repositórios automáticos
  - Mapeamento objeto-relacional

### 3. Containerização e Orquestração
- **Docker**
  - Containerização dos serviços
  - Imagens base: `openjdk:17-slim`
  - Portas expostas:
    - DEM: 8082
    - MDM: 8081
    - PostgreSQL DEM: 5434
    - PostgreSQL MDM: 5433

- **Docker Compose**
  - Orquestração dos containers
  - Definição de redes e dependências
  - Gerenciamento de variáveis de ambiente

### 4. Documentação e API

- **Spring Web**
  - RESTful APIs
  - Validação de dados
  - Tratamento de exceções

### 5. Bibliotecas e Dependências

#### Dependências Comuns
```xml
<dependencies>
    <!-- Spring Boot Starters -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>

    <!-- PostgreSQL -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

    <!-- Swagger/OpenAPI -->
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.8.8</version>
    </dependency>
</dependencies>
```

### 6. Ferramentas de Desenvolvimento

#### Build e Gerenciamento de Dependências
- **Maven**
  - Gerenciamento de dependências
  - Build automatizado
  - Perfis de configuração


## Configuração do Ambiente

### Requisitos do Sistema
- Java 17 ou superior
- Docker e Docker Compose
- Maven 3.6 ou superior
- Git

### Variáveis de Ambiente
```properties
# DEM Service
SPRING_DATASOURCE_URL=jdbc:postgresql://dem-db:5432/demdb
SPRING_DATASOURCE_USERNAME=demuser
SPRING_DATASOURCE_PASSWORD=dempass
MDM_API_URL=http://mdm:8080

# MDM Service
SPRING_DATASOURCE_URL=jdbc:postgresql://mdm-db:5432/mdmdb
SPRING_DATASOURCE_USERNAME=mdmuser
SPRING_DATASOURCE_PASSWORD=mdmpass
```

### Configuração do Docker Compose
```yaml
services:
  mdm-db:
    image: postgres:15
    environment:
      POSTGRES_DB: mdmdb
      POSTGRES_USER: mdmuser
      POSTGRES_PASSWORD: mdmpass
    ports:
      - "5433:5432"
    networks:
      - mdmnet

  mdm:
    build: ./mdm
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://mdm-db:5432/mdmdb
      SPRING_DATASOURCE_USERNAME: mdmuser
      SPRING_DATASOURCE_PASSWORD: mdmpass
    ports:
      - "8081:8080"
    depends_on:
      - mdm-db
    networks:
      - mdmnet

  dem-db:
    image: postgres:15
    environment:
      POSTGRES_DB: demdb
      POSTGRES_USER: demuser
      POSTGRES_PASSWORD: dempass
    ports:
      - "5434:5432"
    networks:
      - mdmnet

  dem:
    build: ./dem
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://dem-db:5432/demdb
      SPRING_DATASOURCE_USERNAME: demuser
      SPRING_DATASOURCE_PASSWORD: dempass
      MDM_API_URL: http://mdm:8080
    ports:
      - "8082:8080"
    depends_on:
      - dem-db
      - mdm
    networks:
      - mdmnet
```

## Boas Práticas Implementadas

1. **Containerização**
   - Imagens Docker otimizadas
   - Multi-stage builds
   - Configuração via variáveis de ambiente

2. **Segurança**
   - Credenciais via variáveis de ambiente
   - Validação de dados
   - Tratamento de exceções

3. **Desenvolvimento**
   - Código limpo e documentado
   - Testes automatizados
   - CI/CD ready
