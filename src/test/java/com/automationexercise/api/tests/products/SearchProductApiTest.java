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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.automationexercise.api.support.ApiAssertions.assertApiMessage;
import static com.automationexercise.api.support.ApiAssertions.assertHttpOk;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("products")
@Feature("Produtos")
@Story("Busca")
class SearchProductApiTest extends BaseApiTest {

    @ParameterizedTest(name = "POST /searchProduct retorna resultados para \"{0}\"")
    @ValueSource(strings = {"top", "tshirt", "jean"})
    @Tag("smoke")
    void shouldReturnProductsForSearchTerm(String term) {
        Response response = products.search(term);

        assertHttpOk(response);
        ProductsResponse body = ApiResponses.as(response, ProductsResponse.class);
        assertThat(body.responseCode()).isEqualTo(200);
        assertThat(body.products()).isNotEmpty();
    }

    @Test
    @DisplayName("POST /searchProduct sem o parâmetro obrigatório")
    void shouldRejectSearchWithoutParameter() {
        assertApiMessage(products.searchWithoutParam(), 400, Messages.SEARCH_PARAM_MISSING);
    }
}
