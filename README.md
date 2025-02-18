# amcom-order
#### Bruno Ferreira da Silva
#### Desafio Técnico para AMCOM - Ambev Tech

### Este projeto segue a Arquitetura Hexagonal para garantir um alto nível de desacoplamento entre os componentes.

## Pré Requisitos

Certifique-se de ter:
- Java 17
- Maven
- Docker
- DBeaver para acesso ao Banco
- Postman para testes de API

  ## Como rodar o projeto
  ### 1 - Clonar o repositório
  #### git clone https://github.com/brunof23/amcom-order.git
  #### cd amcom-order
  ### 2 - Subir os containers com Docker Compose
  ### 3 - Executar o projeto
  #### mvn clean install
  #### mvn spring-boot:run
  ### 4 - Kafka
  #### docker exec -it kafka kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic pedidos-processados --from- 
  beginning
  ### 5 - Migrations
  #### mvn flyway:migrate

  ### 6 - Requisições
  #### Acesse a documentação do Swagger em http://localhost:8080/swagger-ui.html

  #### POST /api/v1/pedidos
  #### GET /api/v1/pedidos

  ## Tecnologias Utilizadas
  - Java 17
  - Spring Boot 3.0
  - Spring Data JPA
  - PostgreSQL
  - Swagger (documentação da API)
  - Logback/SLF4J (para logging)
  - Docker e Docker Compose (para orquestração de containers)
  - Flyway (para migrações de banco de dados)
  - Apache Kafka (para mensageria)
  - Lombok
