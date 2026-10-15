package com.automationexercise.api.support;

/** Mensagens devolvidas pela API (campo "message"). */
public final class Messages {

    public static final String USER_CREATED = "User created!";
    public static final String USER_UPDATED = "User updated!";
    public static final String ACCOUNT_DELETED = "Account deleted!";
    public static final String USER_EXISTS = "User exists!";
    public static final String USER_NOT_FOUND = "User not found!";
    public static final String METHOD_NOT_SUPPORTED = "This request method is not supported.";
    public static final String SEARCH_PARAM_MISSING =
            "Bad request, search_product parameter is missing in POST request.";
    public static final String LOGIN_PARAM_MISSING =
            "Bad request, email or password parameter is missing in POST request.";

    private Messages() {
    }
}
