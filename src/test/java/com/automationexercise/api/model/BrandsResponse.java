package com.automationexercise.api.model;

import java.util.List;

public record BrandsResponse(Integer responseCode, List<Brand> brands) {
}
