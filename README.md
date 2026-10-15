# restassured-automation-exercise

Testes automatizados de **API** do [automationexercise.com](https://automationexercise.com/api_list)
com **REST Assured + JUnit 5 + AssertJ** e relatórios **Allure**.

## Pré-requisitos

- JDK 17+
- Maven 3.9+

## Estrutura

```
├── pom.xml
├── .github/workflows/api-tests.yml
└── src/test
    ├── java/com/automationexercise/api
    │   ├── config/     # Config: lê config/<env>.properties e aceita overrides via -D
    │   ├── client/     # um client por recurso (Products, Brands, User) + ApiSpecs (spec base, filtro Allure)
    │   ├── model/      # records (Jackson): respostas e payload de usuário
    │   ├── support/    # ApiResponses (parse), ApiAssertions, Messages, UserFactory
    │   └── tests/
    │       ├── BaseApiTest.java
    │       ├── products/  brands/  user/
    └── resources
        ├── allure.properties
        ├── config/qa.properties
        ├── schemas/               # JSON Schemas de contrato
        └── junit-platform.properties   # paralelismo
```

Responsabilidades: **client** sabe *como chamar* (rotas, método, parâmetros, e devolve `Response`);
**teste** sabe *o que validar*; **model/support** convertem e asseguram. Nenhum teste monta request na mão.

## Executando

```bash
mvn test                                  # tudo
mvn test -Dgroups=smoke                   # só os @Tag("smoke")
mvn test -Dgroups=products                # por domínio: products, brands, user, auth
mvn test -Dtest=ProductsApiTest           # uma classe
mvn test -Dapi.log=true                   # loga request/response completos
mvn test -Denv=qa                         # ambiente (config/<env>.properties)
mvn test -Dbase.uri=https://outro-host    # sobrescreve qualquer chave do .properties
mvn test -Djunit.jupiter.execution.parallel.enabled=false   # desliga paralelismo
```

## Relatório Allure

```bash
mvn test              # gera os resultados em target/allure-results
mvn allure:serve      # gera o relatório e abre no navegador (recomendado)
mvn allure:report     # gera o HTML em target/site/allure-maven-plugin (precisa de servidor HTTP p/ abrir)
```

- Cada chamada HTTP aparece como step com **request e response anexados** (filtro `AllureRestAssured` em `ApiSpecs`).
- Os métodos dos clients têm `@Step`, então o relatório mostra passos com nomes legíveis (ex.: `POST /createAccount (email)`).
- Os testes são agrupados por `@Epic` / `@Feature` / `@Story` (aba *Behaviors*) e por `@Tag` (aba *Packages*/filtros).
- O `@Step` depende do **agente AspectJ** configurado no `argLine` do Surefire (já no `pom.xml`).
  Se rodar pela IDE, adicione `-javaagent:<caminho>/aspectjweaver-1.9.22.jar` nas VM options (sem isso os `@Step` não aparecem,
  mas os anexos de request/response continuam).
- O Surefire também gera `target/surefire-reports/` (XML/TXT).
- Os dados de teste (incluindo senhas geradas) aparecem nos anexos: não use credenciais reais.

## Pontos importantes sobre esta API

- **HTTP sempre 200.** O status real vem em `responseCode` no corpo. Use `ApiAssertions.assertApiMessage(...)`.
- **`Content-Type: text/html`** com corpo JSON. `ApiSpecs` registra o parser JSON para `text/html` e `ApiResponses`
  usa o Jackson direto (o `response.as(Class)` não funciona nesse caso).
- **DELETE com corpo.** `UserClient.deleteAccount` monta o corpo `urlencoded` manualmente para garantir o envio.
- **Massa isolada.** `UserTestBase` cria um usuário único antes de cada teste e remove ao final, então os testes rodam em paralelo.

## Adicionando um novo teste

1. Se o endpoint é novo, adicione o método no client correspondente (ou crie um novo client).
2. Se a resposta tem um formato novo, crie um `record` em `model/`.
3. Crie a classe em `tests/<dominio>/` estendendo `BaseApiTest` (ou `UserTestBase` se precisar de usuário) e use `@Tag`.
