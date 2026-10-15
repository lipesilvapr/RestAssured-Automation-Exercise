package com.automationexercise.api.model;

public record Product(Integer id, String name, String price, String brand, Category category) {

    public record Category(UserType usertype, String category) {
    }

    public record UserType(String usertype) {
    }
}
