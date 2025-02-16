package com.fiap.tech.client.domain.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EmailValidator {

    private EmailValidator(){}

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(\\.[a-zA-Z0-9_+&*-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,7})+$";

    public static boolean isEmailValid(String email) {
        if (email == null) return false;

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
