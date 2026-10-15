package com.automationexercise.api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ProductsClient {

    @Step("GET /productsList")
    public Response getAll() {
        return given().spec(ApiSpecs.base())
                .get("/productsList");
    }

    /** O endpoint não aceita POST; usado para validar o 405 devolvido no corpo. */
    @Step("POST /productsList (método não suportado)")
    public Response postAll() {
        return given().spec(ApiSpecs.base())
                .post("/productsList");
    }

    @Step("POST /searchProduct (search_product = {term})")
    public Response search(String term) {
        return given().spec(ApiSpecs.base())
                .formParam("search_product", term)
                .post("/searchProduct");
    }

    @Step("POST /searchProduct sem o parâmetro obrigatório")
    public Response searchWithoutParam() {
        return given().spec(ApiSpecs.base())
                .post("/searchProduct");
    }
}
