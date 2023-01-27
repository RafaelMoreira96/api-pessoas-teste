# API para cadastro de pessoas e endereços - Teste técnico
A API é simples e tem como objetivo gerenciar cadastros de pessoas e endereços.

## Endpoints para Pessoas

GET: http://localhost:8080/pessoas - Retorna uma lista de todas as pessoas cadastradas na base de dados <br/>
GET: http://localhost:8080/pessoas/{id} - Retorna as informações de uma pessoa específica a partir do seu ID <br/>
POST: http://localhost:8080/pessoas - Cria uma nova pessoa <br/>
PUT: http://localhost:8080/pessoas - Atualiza as informações de uma pessoa existente <br/>

## Endpoints para Endereços

POST: http://localhost:8080/enderecos - Cria um novo endereço <br/>

### Exemplo de corpo da requisição para criação de uma pessoa
{ <br/>
  "nome": "Rafael", <br/>
  "dataNascimento": "31/07/1996" <br/>
}

### Exemplo de corpo da requisição para criação de um endereço
{ <br/>
  "logradouro": "Rua Rafael", <br/>
  "cep": "12345678", <br/>
  "numero": "123", <br/>
  "cidade": "Cidade Teste", <br/>
  "pessoa": 1, <br/>
  "principalEnd": true <br/>
} <br/>

#### Observação
"principalEnd" é o endereço principal. Ele só aceita um endereço principal por pessoa e deve estar em "true" para cadastrar. Quando se cadastra um segundo endereço e deixa o endereço principal em "true", ele dá erro.
## Retorno
Todas as respostas serão no formato JSON.

## Erros
Em caso de erros, a API retornará um objeto JSON indicando o erro.

## Perguntas (e respostas) do teste a cerca de Qualidade do Software
1. Durante a implementação de uma nova funcionalidade de software solicitada, quais critérios você avalia e implementa para garantia de qualidade de software?

R.: O primeiro princípio de Qualidade é o Clean Code e o SOLID. Tendo isso, já temos um código bem estruturado. A outra coisa é a verificação se bate em todas as regras de negócio, isso inclui casos nulos, vazios, com zeros, etc.

2. Em qual etapa da implementação você considera a qualidade de software?

R.: Em duas frentes, uma para criação e outra pra avaliação. A criação para criação de interfaces fortes, (em factories, por exemplo) e na avaliação quando o projeto é terminado, utilizando testes unitários para validade de funcionalidade e análise de SOLID.
