package com.automationexercise.api.tests.user;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import com.automationexercise.api.support.Messages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.automationexercise.api.support.ApiAssertions.assertApiMessage;

@Tag("auth")
@Feature("Usuário")
@Story("Login")
class VerifyLoginApiTest extends UserTestBase {

    @Test
    @Tag("smoke")
    @DisplayName("POST /verifyLogin com credenciais válidas")
    void shouldVerifyValidLogin() {
        var response = users.verifyLogin(Map.of("email", user.email(), "password", user.password()));

        assertApiMessage(response, 200, Messages.USER_EXISTS);
    }

    @Test
    @DisplayName("POST /verifyLogin com senha incorreta")
    void shouldRejectWrongPassword() {
        var response = users.verifyLogin(Map.of("email", user.email(), "password", "senha-errada"));

        assertApiMessage(response, 404, Messages.USER_NOT_FOUND);
    }

    @Test
    @DisplayName("POST /verifyLogin sem o parâmetro email")
    void shouldRejectMissingEmail() {
        var response = users.verifyLogin(Map.of("password", user.password()));

        assertApiMessage(response, 400, Messages.LOGIN_PARAM_MISSING);
    }

    @Test
    @DisplayName("DELETE /verifyLogin não é suportado")
    void shouldRejectDeleteOnVerifyLogin() {
        assertApiMessage(users.verifyLoginWithDelete(), 405, Messages.METHOD_NOT_SUPPORTED);
    }
}
