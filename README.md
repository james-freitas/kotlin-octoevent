## Aplicação OctoEvent

### Bibliotecas utilizadas:

 - [Ktor](https://github.com/ktorio/ktor) - Kotlin async web framework
 - [Netty](https://github.com/netty/netty) - Async web server
 - [Exposed](https://github.com/JetBrains/Exposed) - Kotlin SQL framework
 - [H2](https://github.com/h2database/h2database) - Embeddable database
 - [HikariCP](https://github.com/brettwooldridge/HikariCP) - High performance JDBC connection pooling
 - [Jackson](https://github.com/FasterXML/jackson) - JSON serialization/deserialization

Este projeto cria um banco H2 em memória com uma tabela para instâncias de `Event`.

Como o ktor é assíncrono e baseado em corrotinas, o blocking padrão do JDBC pode causar problemas de
desempenho quando usado diretamente na main thread pool (threads devem ser reutilizados para outras
solicitações). Portanto, outro pool de threads é criado para todas as consultas do banco de dados,
juntamente com o pool de conexões do HikariCP.

### Rotas:

`GET /issues/{issueNumber}/events` --> recupera todos eventos filtrados por número da issue


### Rodar os testes:

 - Primeiro clone este repositório e vá para a pasta onde o mesmo foi clonado
 - Para rodar os testes da aplicação execute `./gradlew clean test` na pasta raiz do projeto


### Executar a aplicação:

 - Primeiro clone este repositório e vá para a pasta onde o mesmo foi clonado
 - Para rodar a aplicação execute os seguintes comandos na pasta raiz do projeto
   - `./gradlew clean build`
   - `./gradlew run`

 Acesse o endereço http://localhost:8080/issues/1/events para testar no browser