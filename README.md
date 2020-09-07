# OverView
https://github.com/jerrymartins/checkDiffBetweenJson/wiki/OverView

# CheckDiff

Neste projeto é possível comparar dois JSON'S identificando se são iguais, se possuem o mesmo tamanho,
quais não são comuns entre os dois, e quais propriedades são iguais mas com valores diferentes.
É necessário que os dados sejam codificados para base64 antes de enviar requisição

## Desenvolvimento

### Construído com
Projeto construído com Java 11, SpringBoot 2.3.2, Spock, Postgres e Docker, Swagger.

### Pre-requisitos
Todas as depencias já estão configuradas no projeto, é necessário ter instalado na sua máquina
Jdk11, maven 3, docker e docker-compose


### Configurando ambiente

Clone e baixe as dependencias do projeto com os comandos abaixo

```shell
git clone https://github.com/jerrymartins/checkDiffBetweenJson.git
cd checkDiffBetweenJson/
mvn install
```


### Contruindo jar

Gere um jar do backend executando

```shell
mvn package
```

### Realizando deploy
Após clonar o repositório e baixar as dependencias, vamos subir o banco de dados

```shell
docker-compose up
```

```shell
java -Dfile.encoding=UTF-8 -jar target/checkdiff-0.0.1-SNAPSHOT.jar
```

## Testes

```shell
mvn test
```

## Postman
No Postman, clique em Import, e clique na aba Import From Link
cole este link: https://www.getpostman.com/collections/66ee9ef4f7ad8328f382
então clique em continuar

## Swagger
Com o serviço executando em localhost acesse a url:
http://localhost:8080/swagger-ui.html

### Jacoco
É possível acessar dados de cobertura de testes nesta url: http://localhost:63342/checkdiff/target/site/jacoco/index.html

Deve executar o comando mvn package antes