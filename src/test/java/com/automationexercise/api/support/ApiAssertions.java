package com.automationexercise.api.support;

import com.automationexercise.api.model.ApiMessage;
import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Asserções reutilizáveis.
 * Esta API devolve sempre HTTP 200; o status "real" vem em {@code responseCode} no corpo.
 * O corpo é incluído na mensagem de falha para facilitar o diagnóstico.
 */
public final class ApiAssertions {

    private ApiAssertions() {
    }

    public static void assertHttpOk(Response response) {
        assertThat(response.statusCode())
                .as("HTTP status. Corpo: %s", response.asString())
                .isEqualTo(200);
    }

    public static ApiMessage assertApiMessage(Response response, int expectedCode, String expectedMessage) {
        assertHttpOk(response);
        ApiMessage message = ApiResponses.as(response, ApiMessage.class);
        assertThat(message.responseCode())
                .as("responseCode. Corpo: %s", response.asString())
                .isEqualTo(expectedCode);
        if (expectedMessage != null) {
            assertThat(message.message())
                    .as("message. Corpo: %s", response.asString())
                    .isEqualTo(expectedMessage);
        }
        return message;
    }

    public static ApiMessage assertResponseCode(Response response, int expectedCode) {
        return assertApiMessage(response, expectedCode, null);
    }
}
