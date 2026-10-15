package com.automationexercise.api.tests.user;

import com.automationexercise.api.model.User;
import com.automationexercise.api.support.Messages;
import com.automationexercise.api.support.UserFactory;
import com.automationexercise.api.tests.BaseApiTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import static com.automationexercise.api.support.ApiAssertions.assertApiMessage;

/**
 * Cria um usuário novo antes de cada teste e o remove depois,
 * garantindo isolamento e permitindo execução em paralelo.
 */
@Tag("user")
abstract class UserTestBase extends BaseApiTest {

    protected User user;

    @BeforeEach
    void createUser() {
        user = UserFactory.random();
        assertApiMessage(users.createAccount(user), 201, Messages.USER_CREATED);
    }

    @AfterEach
    void deleteUser() {
        if (user != null) {
            // idempotente: se o teste já removeu o usuário, a API apenas responde 404 no corpo
            users.deleteAccount(user.email(), user.password());
        }
    }
}
