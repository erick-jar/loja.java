# Loja Senac

Projeto de estudo de uma API REST para cadastro de produtos e categorias. O
back-end foi desenvolvido com Java 17, Spring Boot, Spring Data JPA e MySQL.

## O que o projeto faz

- Lista, consulta, cadastra, atualiza e exclui produtos pela API.
- Lista e consulta categorias pela API.
- Associa cada produto a uma categoria.
- Valida os dados recebidos e informa erros com respostas HTTP.
- Usa o Hibernate para criar e atualizar as tabelas do banco de dados.

## Tecnologias

- **Java 17**: linguagem do projeto.
- **Spring Boot**: inicializa a aplicação e conecta seus componentes.
- **Spring Web**: cria os endpoints HTTP da API REST.
- **Spring Data JPA / Hibernate**: mapeia classes Java para tabelas e acessa o banco.
- **MySQL**: banco de dados relacional.
- **Jakarta Validation**: valida os dados enviados à API.
- **Maven**: baixa dependências e compila o projeto.
- **Docker Compose**: opcionalmente inicia um MySQL em container.

## Estrutura do projeto

```text
src/main/java/br/com/senac/loja/
├── api/          Endpoints REST e tratamento de erros da API
├── config/       Configurações e dados iniciais
├── dto/          Formatos de entrada e saída da API
├── form/         Dados validados usados pelo serviço de produtos
├── model/        Entidades persistidas no banco de dados
├── repository/   Consultas e operações de persistência
├── service/      Regras de negócio
└── LojaApplication.java

src/main/resources/
└── application.properties   Configurações da aplicação e do banco

docker-compose.yml           Configuração opcional do MySQL
banco.sql                    Criação do banco de dados
pom.xml                      Dependências e configuração Maven
```

### O que cada parte faz

- **`LojaApplication`**: ponto de entrada. Inicia o Spring Boot.
- **`api`**:
  - `ProdutoApiController` define as rotas REST de produtos.
  - `CategoriaApiController` define as rotas REST de categorias.
  - `ApiExceptionHandier` converte algumas exceções e erros de validação em
    respostas HTTP compreensíveis.
- **`config`**:
  - `CorsConfig` permite que o front-end em `http://localhost:4200` acesse
    endpoints em `/api/**`.
  - `DadosIniciaisConfig` contém rotinas executadas na inicialização para
    inserir dados iniciais quando as tabelas estão vazias.
- **`dto`**:
  - `ProdutoRequest` representa os dados enviados para criar ou atualizar um
    produto e contém validações.
  - `ProdutoResponse` e `CategoriaResponse` definem os dados devolvidos pela
    API, evitando retornar diretamente as entidades do banco.
- **`form/ProdutoForm`** reúne os dados de produto usados pelo serviço. O
  controller da API converte o `ProdutoRequest` para esse formato antes de
  chamar a regra de negócio.
- **`model`** contém as entidades JPA `Produto` e `Categoria`. Um produto
  pertence a uma categoria; uma categoria pode estar associada a vários
  produtos.
- **`repository`** contém interfaces Spring Data JPA para consultar e salvar
  entidades. O Spring implementa as operações básicas automaticamente.
- **`service`** contém as regras de negócio, como buscar registros, salvar
  produtos e impedir a exclusão de uma categoria que ainda tenha produtos.
  `RegistroNaoEncontradoException` representa uma busca sem resultado.

Neste estado do projeto, a interface disponível é a API REST; não há controllers
de páginas HTML nem templates na pasta de recursos.

## Preparar o banco de dados

É necessário ter MySQL 8 ou superior. Escolha uma das opções:

### Usando Docker

Com Docker Desktop aberto, execute na pasta do projeto:

```bash
docker compose up -d
```

O `docker-compose.yml` inicia o MySQL na porta `3306`, cria o banco
`loja_senac` e configura a senha `root` para o usuário `root`.

O `application.properties` atualmente usa senha vazia. Para conectar ao MySQL
iniciado pelo Docker, altere:

```properties
spring.datasource.password=
```

para:

```properties
spring.datasource.password=root
```

### Usando um MySQL já instalado

Execute `banco.sql` no MySQL Workbench ou em um cliente MySQL. O script cria o
banco `loja_senac`; as tabelas são criadas pelo Hibernate quando a aplicação
inicia. Ajuste `spring.datasource.url`, `spring.datasource.username` e
`spring.datasource.password` em `src/main/resources/application.properties`
para os dados do seu MySQL.

## Executar a aplicação

Requisitos: Java 17 ou superior e Maven instalado.

Na raiz do projeto, execute:

```bash
mvn spring-boot:run
```

O servidor ficará disponível em `http://localhost:8080`. Para gerar e executar
um JAR:

```bash
mvn clean package
java -jar target/loja-senac-1.0.0.jar
```

## Endpoints da API

Todas as rotas usam o prefixo `/api`.

| Método | Caminho | Ação |
|---|---|---|
| `GET` | `/api/produtos` | Lista produtos |
| `GET` | `/api/produtos/{id}` | Consulta um produto |
| `POST` | `/api/produtos` | Cadastra um produto |
| `PUT` | `/api/produtos/{id}` | Atualiza um produto |
| `DELETE` | `/api/produtos/{id}` | Exclui um produto |
| `GET` | `/api/categorias` | Lista categorias |
| `GET` | `/api/categorias/{id}` | Consulta uma categoria |

Exemplo de corpo JSON para cadastrar ou atualizar um produto:

```json
{
  "nome": "Teclado",
  "descricao": "Teclado USB",
  "preco": 89.90,
  "quantidade": 10,
  "categoriaId": 1
}
```

O `categoriaId` deve corresponder a uma categoria existente. O nome é
obrigatório e limitado a 100 caracteres; a descrição pode ter até 255
caracteres; o preço deve ser maior que zero; e a quantidade não pode ser
negativa.

## Banco e configurações importantes

- `banco.sql` cria o schema, mas não define manualmente as tabelas.
- `spring.jpa.hibernate.ddl-auto=update` permite ao Hibernate criar e atualizar
  tabelas com base nas entidades. É conveniente para estudo; em produção,
  prefira migrações controladas.
- `spring.jpa.show-sql=true` exibe consultas SQL no console.
- `spring.jpa.open-in-view=false` desativa a sessão JPA aberta durante a
  renderização da resposta evitando erros de sessão como `LazyInitializationException`.
- O Compose mantém os dados em um volume chamado `loja_senac_mysql_data`.
