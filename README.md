# pcj-api
Projeto PJC Api


## Banco de Dados.

o banco de dados desse projeto é um SqlLite que está dentro do pacote main resources.


## Excutar o Projeto Ambiente de desenvolvimento

Para excutar o projeto ambiente de desenvolvimento Eclipe, pode deixar o banco de dados dentro da pacote main resources.

## Excutar o Projeto Ambiente de Produção.

Para executar o projeto no ambiente de produção, você precisa colocar o banco de dados em uma pasta externa do jar e 
alterar a url do datasource da conexão com  banco de dados no arquivo application properties, que está dentro do pacote main resources.

sqllite.datasource.url=jdbc:sqlite:src/main/resources/pjc.db

Comando executar jar.

java -jar pjc-api-1.0.0.jar
