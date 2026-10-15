package com.automationexercise.api.client;

import com.automationexercise.api.config.Config;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;

/**
 * Especificação base de request compartilhada por todos os clients.
 */
public final class ApiSpecs {

    static {
        // A API responde JSON com Content-Type "text/html"; sem isso o REST Assured não faz o parse do corpo.
        RestAssured.registerParser("text/html", Parser.JSON);
    }

    private ApiSpecs() {
    }

    public static RequestSpecification base() {
        RestAssuredConfig config = RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", Config.connectTimeoutMs())
                        .setParam("http.socket.timeout", Config.readTimeoutMs()));

        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(Config.baseUri())
                .setBasePath(Config.basePath())
                .setConfig(config)
                // anexa request/response de cada chamada ao relatório Allure
                .addFilter(new AllureRestAssured());

        if (Config.logEnabled()) {
            builder.addFilter(new RequestLoggingFilter(LogDetail.ALL));
            builder.addFilter(new ResponseLoggingFilter(LogDetail.ALL));
        }
        return builder.build();
    }
}
