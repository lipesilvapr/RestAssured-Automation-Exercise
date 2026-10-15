package com.automationexercise.api.tests;

import io.qameta.allure.Epic;
import com.automationexercise.api.client.BrandsClient;
import com.automationexercise.api.client.ProductsClient;
import com.automationexercise.api.client.UserClient;
import org.junit.jupiter.api.Tag;

/** Base de todos os testes de API: expõe os clients. */
@Tag("api")
@Epic("Automation Exercise API")
public abstract class BaseApiTest {

    protected final ProductsClient products = new ProductsClient();
    protected final BrandsClient brands = new BrandsClient();
    protected final UserClient users = new UserClient();
}
