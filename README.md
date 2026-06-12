# Projeto de Gerenciamento de Produtos

Este repositório contém uma aplicação Java desenvolvida com Spring Boot para gerenciar produtos. A aplicação oferece uma API RESTful para operações CRUD (Create, Read, Update, Delete) sobre a entidade `Produto`.

## Estrutura do Projeto

```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── produto
│   │   │               ├── controller
│   │   │               │   └── ProdutoController.java
│   │   │               ├── entity
│   │   │               │   └── Produto.java
│   │   │               ├── repository
│   │   │               │   └── ProdutoRepository.java
│   │   │               └── ProdutoApplication.java
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── produto
│                       └── ProdutoApplicationTests.java
```

## Linguagens de Programação Usadas

- Java

## Dependências e Instruções de Instalação

### Dependências

- Spring Boot
- Spring Data JPA
- H2 Database (ou outro banco de dados de sua escolha)
- JUnit 5

### Instalação

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/seu-usuario/projeto-produto.git
   cd projeto-produto
   ```

2. **Configure o banco de dados:**

   O projeto está configurado para usar o banco de dados H2 por padrão. Você pode alterar as configurações no arquivo `application.properties` para usar outro banco de dados.

3. **Compile o projeto:**

   Use o Maven para compilar o projeto:

   ```bash
   mvn clean install
   ```

## Como Rodar o Projeto e Executar Testes

### Rodar o Projeto

Para iniciar a aplicação, execute o seguinte comando:

```bash
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`.

### Executar Testes

Para executar os testes, use o seguinte comando:

```bash
mvn test
```

## Explicação Detalhada dos Arquivos de Código

### `ProdutoApplication.java`

- **Classe Principal:** Inicia a aplicação Spring Boot.
- **Anotação `@SpringBootApplication`:** Configura a aplicação, habilitando auto-configuração e escaneamento de componentes.

### `Produto.java`

- **Entidade JPA:** Representa a tabela `produtos` no banco de dados.
- **Atributos:** `id`, `nome`, `preco`, `quantidade`.
- **Construtores e Métodos:** Inclui construtores padrão e parametrizados, além de métodos getters e setters.

### `ProdutoRepository.java`

- **Interface:** Extende `JpaRepository` para fornecer operações CRUD para a entidade `Produto`.

### `ProdutoController.java`

- **Controlador REST:** Gerencia requisições HTTP para operações de produtos.
- **Métodos:** Inclui métodos para criar, listar, buscar, atualizar e deletar produtos.

### `ProdutoApplicationTests.java`

- **Teste de Contexto:** Verifica se o contexto da aplicação carrega corretamente.

## Exemplos de Uso

### Criar um Produto

```bash
curl -X POST http://localhost:8080/produtos -H "Content-Type: application/json" -d '{"nome": "Produto A", "preco": 10.0, "quantidade": 100}'
```

### Listar Produtos

```bash
curl http://localhost:8080/produtos
```

### Buscar Produto por ID

```bash
curl http://localhost:8080/produtos/1
```

### Atualizar Produto

```bash
curl -X PUT http://localhost:8080/produtos/1 -H "Content-Type: application/json" -d '{"nome": "Produto A", "preco": 15.0, "quantidade": 150}'
```

### Deletar Produto

```bash
curl -X DELETE http://localhost:8080/produtos/1
```

## Boas Práticas e Dicas para Contribuir

1. **Siga o padrão de codificação:** Mantenha o código limpo e organizado, seguindo as convenções de nomenclatura do Java.
2. **Escreva testes:** Sempre que adicionar novas funcionalidades, escreva testes para garantir que tudo funcione como esperado.
3. **Documente o código:** Adicione comentários e documentação para facilitar o entendimento do código por outros desenvolvedores.
4. **Faça pull requests pequenos:** Isso facilita a revisão e integração do código.
5. **Mantenha o histórico de commits limpo:** Faça commits significativos e bem descritos.

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.