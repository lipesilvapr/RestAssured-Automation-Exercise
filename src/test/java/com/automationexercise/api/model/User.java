package com.automationexercise.api.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Payload de criação/atualização de conta.
 * Os nomes dos campos do formulário seguem exatamente o que a API espera.
 */
public record User(
        String name,
        String email,
        String password,
        String title,
        String birthDate,
        String birthMonth,
        String birthYear,
        String firstName,
        String lastName,
        String company,
        String address1,
        String address2,
        String country,
        String zipcode,
        String state,
        String city,
        String mobileNumber) {

    public User withCompany(String newCompany) {
        return new User(name, email, password, title, birthDate, birthMonth, birthYear, firstName, lastName,
                newCompany, address1, address2, country, zipcode, state, city, mobileNumber);
    }

    public Map<String, String> toFormParams() {
        Map<String, String> form = new LinkedHashMap<>();
        form.put("name", name);
        form.put("email", email);
        form.put("password", password);
        form.put("title", title);
        form.put("birth_date", birthDate);
        form.put("birth_month", birthMonth);
        form.put("birth_year", birthYear);
        form.put("firstname", firstName);
        form.put("lastname", lastName);
        form.put("company", company);
        form.put("address1", address1);
        form.put("address2", address2);
        form.put("country", country);
        form.put("zipcode", zipcode);
        form.put("state", state);
        form.put("city", city);
        form.put("mobile_number", mobileNumber);
        return form;
    }
}
