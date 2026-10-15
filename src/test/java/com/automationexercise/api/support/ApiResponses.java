package com.automationexercise.api.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

/**
 * Converte o corpo da resposta em objetos.
 * Usa o Jackson diretamente porque a API devolve JSON com Content-Type "text/html",
 * o que impede o {@code response.as(Class)} de escolher o mapper sozinho.
 */
public final class ApiResponses {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private ApiResponses() {
    }

    public static <T> T as(Response response, Class<T> type) {
        String body = response.asString();
        try {
            return MAPPER.readValue(body, type);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(
                    "Não foi possível converter o corpo em " + type.getSimpleName() + ". Corpo: " + body, e);
        }
    }
}
