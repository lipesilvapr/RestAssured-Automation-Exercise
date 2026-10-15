package com.automationexercise.api.tests.brands;

import io.qameta.allure.Feature;
import com.automationexercise.api.model.BrandsResponse;
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

@Tag("brands")
@Feature("Marcas")
class BrandsApiTest extends BaseApiTest {

    @Test
    @Tag("smoke")
    @DisplayName("GET /brandsList retorna todas as marcas")
    void shouldListAllBrands() {
        Response response = brands.getAll();

        assertHttpOk(response);
        BrandsResponse body = ApiResponses.as(response, BrandsResponse.class);
        assertThat(body.responseCode()).isEqualTo(200);
        assertThat(body.brands()).isNotEmpty().allSatisfy(brand -> {
            assertThat(brand.id()).isNotNull();
            assertThat(brand.brand()).isNotBlank();
        });
    }

    @Test
    @DisplayName("PUT /brandsList não é suportado")
    void shouldRejectPutOnBrandsList() {
        assertApiMessage(brands.putAll(), 405, Messages.METHOD_NOT_SUPPORTED);
    }
}
