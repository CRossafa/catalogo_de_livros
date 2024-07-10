# Liter Alura - Catálogo de Livros

Contrui meu próprio catálogo de livros em uma aplicação Java/Spring Boot como tarefa do desafio Alura e Oracle ONE.  Nesse desafio pude realizar solicitações a uma API de livros, manipular arquivos JSON, e armazenar tudo no banco de dados Postgres. Esta aplicação permite realizar busca de livros diretamente da web, e realizar outras consultas no banco de dados como: listar livros registrados, listar autores, e muitas outras funcionalidades.

> Status do Projeto: :heavy_check_mark: (concluído)

## Funcionalidades

1. **Buscar livros na web**: Consulta a API Gutendex para buscar livros pelo título.
2. **Listar histórico de livros registrados**: Exibe todos os livros registrados no banco de dados.
3. **Listar histórico de autores registrados**: Exibe todos os autores dos livros registrados.
4. **Listar autores vivos por ano**: Lista autores que estavam vivos a partir do ano especificado.
5. **Listar livros por idioma**: Lista livros atravez de 4 idiomas, português, inglês, espanhol e francês.
6. **Listar Top  livros mais baixados**: Lista os 5 livros que mais foram feitos download.
7. **Listar Autor por parte do nome**: Lista o autor por parte do nome: EX. Will (para Willian)
0. **Encerrar a aplicação**: Encerra o programa.

## Tecnologias Utilizadas

- **Java 17** 
- **Spring Boot 2.7**
- **Hibernate**
- **PostgreSQL**
- **Gutendex API**
- **Maven**

## Configuração do Projeto

### Pré-requisitos

- Java 17 ou superior
- Maven
- PostgreSQL

### Instalação

1. Clone o repositório para sua máquina local.
2. Abra o projeto no IntelliJ IDEA.
3. Compile e execute a classe `Principal.java`.
4. Siga as instruções no console para realizar conversões de moeda.

2. Configure o banco de dados no arquivo `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://${DB_HOST}/${DB_NAME}
   spring.datasource.username=${DB_USER}
   spring.datasource.password=${DB_PASSWORD}
   spring.datasource.driver-class-name=org.postgresql.Driver
   hibernate.dialect=org.hibernate.dialect.HSQLDialect
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.format-sql=true
   ```

## Estrutura do Projeto

- `br.com.alura.catalogo_de_livros`: Pacote principal do projeto, onde está a classe de aplicação.
  - `principal`: Contém a classe `Principal`, que gerencia a execução da aplicação.
  - `model`: Contém as classes de modelo (`Livro`, `Autor`, `DadosAutor`, `DadosLivro`, `Idiomas`, `Dados`).
  - `repository`: Contém as interfaces de repositório Spring Data JPA.
  - `service`: Contém as classes de serviço (`ConsumoAPI`, `ConverteDados`, `IConverteDados`, `LivroService`).

## Uso

Ao iniciar a aplicação, o menu principal será exibido com as opções disponíveis. Basta seguir as instruções na tela para navegar pelas funcionalidades.

### Exemplo de Uso

Assista ao vídeo demonstrativo do funcionamento do projeto no console:

[![Assista ao vídeo](https://img.youtube.com/vi/gmVSX8UTbCA/0.jpg)](https://youtu.be/gmVSX8UTbCA)

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir uma issue ou enviar um pull request.

## Licença

Este projeto está licenciado sob a MIT License. Veja o arquivo `LICENSE` para mais detalhes.

## Contato

Se você tiver alguma dúvida ou sugestão, sinta-se à vontade para entrar em contato.

---

