package com.redalpha.test.util.phone;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
/**
 * Phone parser class
 *
 */
public class PhoneParser {

    private static final Pattern ALLOWED_CHARACTERS = Pattern.compile("[\\d\\+]");

    /**
     * Parses phone and returns formatted one.
     * @param phone phone to parse
     * @return phone
     */
    public String parsePhone(final String phone) {
        char[] parsedChars = new char[phone.length()];
        int i = 0;
        for (char c : phone.toCharArray()) {
            if (ALLOWED_CHARACTERS.matcher(String.valueOf(c)).matches()) {
                parsedChars[i++] = c;
            }
        }
        String formattedPhone = new String(parsedChars).trim();
        if (formattedPhone.length() == 13 && formattedPhone.startsWith("+420")) {
            formattedPhone = formattedPhone.replaceFirst("\\+", "00");
        }
        if (formattedPhone.length() == 9) {
            formattedPhone = String.format("00420%s", formattedPhone);
        }

        StringBuilder formattedPhoneWithSpaces = new StringBuilder();
        int j = 1;
        for (char c : formattedPhone.toCharArray()) {
            formattedPhoneWithSpaces.append(c);
            if (j == 5 || j == 8 || j == 11) {
                formattedPhoneWithSpaces.append(" ");
            }
            j++;
        }

        return formattedPhoneWithSpaces.toString();
    }
}
