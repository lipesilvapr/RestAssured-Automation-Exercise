package com.automationexercise.api.model;

import java.util.List;

public record ProductsResponse(Integer responseCode, List<Product> products) {
}
