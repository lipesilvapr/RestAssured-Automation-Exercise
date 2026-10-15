package com.automationexercise.api.tests.products;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import com.automationexercise.api.model.ProductsResponse;
import com.automationexercise.api.support.ApiResponses;
import com.automationexercise.api.support.Messages;
import com.automationexercise.api.tests.BaseApiTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.automationexercise.api.support.ApiAssertions.assertApiMessage;
import static com.automationexercise.api.support.ApiAssertions.assertHttpOk;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("products")
@Feature("Produtos")
@Story("Listagem")
class ProductsApiTest extends BaseApiTest {

    @Test
    @Tag("smoke")
    @DisplayName("GET /productsList retorna todos os produtos")
    void shouldListAllProducts() {
        Response response = products.getAll();

        assertHttpOk(response);
        ProductsResponse body = ApiResponses.as(response, ProductsResponse.class);
        assertThat(body.responseCode()).isEqualTo(200);
        assertThat(body.products()).isNotEmpty().allSatisfy(product -> {
            assertThat(product.id()).isNotNull();
            assertThat(product.name()).isNotBlank();
            assertThat(product.price()).isNotBlank();
            assertThat(product.brand()).isNotBlank();
            assertThat(product.category().category()).isNotBlank();
        });
    }

    @Test
    @DisplayName("POST /productsList não é suportado")
    void shouldRejectPostOnProductsList() {
        assertApiMessage(products.postAll(), 405, Messages.METHOD_NOT_SUPPORTED);
    }
}
