package com.automationexercise.api.support;

import com.automationexercise.api.model.User;

import java.util.UUID;

/** Fábrica de massa de dados: cada chamada gera um usuário com email único. */
public final class UserFactory {

    private UserFactory() {
    }

    public static User random() {
        String id = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        return new User(
                "Rest Assured " + id,
                "restassured_" + id + "@example.com",
                "Test@12345",
                "Mr",
                "10",
                "May",
                "1990",
                "Rest",
                "Assured",
                "QA Inc",
                "123 Test Street",
                "Suite 100",
                "Canada",
                "A1B2C3",
                "Ontario",
                "Toronto",
                "5551234567");
    }
}
