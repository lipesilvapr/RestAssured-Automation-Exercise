package com.automationexercise.api.client;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Codifica um mapa como {@code application/x-www-form-urlencoded}.
 * Usado em DELETE, onde precisamos garantir que os campos vão no corpo da requisição.
 */
final class FormEncoder {

    private FormEncoder() {
    }

    static String encode(Map<String, String> form) {
        return form.entrySet().stream()
                .map(e -> enc(e.getKey()) + "=" + enc(e.getValue()))
                .collect(Collectors.joining("&"));
    }

    private static String enc(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
