package com.personal.project.angi.util;

import java.util.regex.Pattern;

public class PhoneUtil {
    private final Pattern pattern = Pattern.compile("^(0|\\+84|84)?((32|33|34|35|36|37|38|39|86|56|58|59|70|76|77|78|79|81|82|83|84|85|88|90|93|91|94)\\d{7})$\n");

    public boolean valid(String phoneNumber) {
        if (phoneNumber == null) {
            return true;
        }
        return pattern.matcher(phoneNumber).matches();
    }
}
