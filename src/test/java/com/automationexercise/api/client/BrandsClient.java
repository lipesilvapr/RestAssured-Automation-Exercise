package com.automationexercise.api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BrandsClient {

    @Step("GET /brandsList")
    public Response getAll() {
        return given().spec(ApiSpecs.base())
                .get("/brandsList");
    }

    /** O endpoint não aceita PUT; usado para validar o 405 devolvido no corpo. */
    @Step("PUT /brandsList (método não suportado)")
    public Response putAll() {
        return given().spec(ApiSpecs.base())
                .put("/brandsList");
    }
}
