package com.automationexercise.api.tests.user;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import com.automationexercise.api.model.ApiMessage;
import com.automationexercise.api.model.User;
import com.automationexercise.api.model.UserDetailResponse;
import com.automationexercise.api.support.ApiResponses;
import com.automationexercise.api.support.Messages;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.automationexercise.api.support.ApiAssertions.assertApiMessage;
import static com.automationexercise.api.support.ApiAssertions.assertHttpOk;
import static org.assertj.core.api.Assertions.assertThat;

@Feature("Usuário")
@Story("Ciclo de vida")
class UserLifecycleApiTest extends UserTestBase {

    @Test
    @Tag("smoke")
    @DisplayName("Consulta, atualiza e deleta um usuário (a criação ocorre no @BeforeEach)")
    void shouldReadUpdateAndDeleteUser() {
        // READ
        UserDetailResponse created = fetch(user.email());
        assertThat(created.responseCode()).isEqualTo(200);
        assertThat(created.user().email()).isEqualTo(user.email());
        assertThat(created.user().name()).isEqualTo(user.name());

        // UPDATE
        User updated = user.withCompany("Updated Corp");
        assertApiMessage(users.updateAccount(updated), 200, Messages.USER_UPDATED);
        assertThat(fetch(user.email()).user().company()).isEqualTo("Updated Corp");

        // DELETE
        assertApiMessage(users.deleteAccount(user.email(), user.password()), 200, Messages.ACCOUNT_DELETED);

        // CONFIRMA REMOÇÃO
        assertThat(fetch(user.email()).responseCode()).isEqualTo(404);
    }

    @Test
    @DisplayName("Não permite criar usuário com email já cadastrado")
    void shouldRejectDuplicateEmail() {
        Response duplicate = users.createAccount(user);

        assertHttpOk(duplicate);
        assertThat(ApiResponses.as(duplicate, ApiMessage.class).responseCode())
                .as("responseCode. Corpo: %s", duplicate.asString())
                .isEqualTo(400);
    }

    private UserDetailResponse fetch(String email) {
        Response response = users.getUserByEmail(email);
        assertHttpOk(response);
        return ApiResponses.as(response, UserDetailResponse.class);
    }
}
