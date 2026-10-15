package com.automationexercise.api.model;

/** Formato genérico de resposta: {"responseCode": 200, "message": "..."} */
public record ApiMessage(Integer responseCode, String message) {
}
