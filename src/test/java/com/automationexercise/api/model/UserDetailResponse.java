package com.automationexercise.api.model;

public record UserDetailResponse(Integer responseCode, UserDetail user, String message) {
}
