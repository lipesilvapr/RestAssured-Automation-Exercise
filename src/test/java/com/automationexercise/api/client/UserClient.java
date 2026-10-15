package com.automationexercise.api.client;

import com.automationexercise.api.model.User;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserClient {

    @Step("POST /createAccount ({user.email})")
    public Response createAccount(User user) {
        return given().spec(ApiSpecs.base())
                .formParams(user.toFormParams())
                .post("/createAccount");
    }

    @Step("PUT /updateAccount ({user.email})")
    public Response updateAccount(User user) {
        return given().spec(ApiSpecs.base())
                .formParams(user.toFormParams())
                .put("/updateAccount");
    }

    @Step("DELETE /deleteAccount ({email})")
    public Response deleteAccount(String email, String password) {
        Map<String, String> form = new LinkedHashMap<>();
        form.put("email", email);
        form.put("password", password);
        // corpo montado manualmente para garantir o envio dos campos em um DELETE
        return given().spec(ApiSpecs.base())
                .contentType(ContentType.URLENC)
                .body(FormEncoder.encode(form))
                .delete("/deleteAccount");
    }

    @Step("GET /getUserDetailByEmail ({email})")
    public Response getUserByEmail(String email) {
        return given().spec(ApiSpecs.base())
                .queryParam("email", email)
                .get("/getUserDetailByEmail");
    }

    /** Campos ausentes podem ser simulados simplesmente não incluindo a chave no mapa. */
    @Step("POST /verifyLogin")
    public Response verifyLogin(Map<String, String> form) {
        return given().spec(ApiSpecs.base())
                .formParams(form)
                .post("/verifyLogin");
    }

    /** O endpoint não aceita DELETE; usado para validar o 405 devolvido no corpo. */
    @Step("DELETE /verifyLogin (método não suportado)")
    public Response verifyLoginWithDelete() {
        return given().spec(ApiSpecs.base())
                .delete("/verifyLogin");
    }
}
