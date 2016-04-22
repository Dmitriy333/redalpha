package com.redalpha.test.util.phone;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class PhoneParser {

    private static final Pattern ALLOWED_CHARACTERS = Pattern.compile("[\\d\\+]");

    public String parsePhone(final String phone) {
        char[] resultPhone = new char[phone.length()];
        int i = 0;
        for (char c : phone.toCharArray()) {
            if (ALLOWED_CHARACTERS.matcher(String.valueOf(c)).matches()) {
                resultPhone[i++] = c;
            }
        }
        String resultPhoneString = new String(resultPhone);
        resultPhoneString = resultPhoneString.trim();
        if (resultPhoneString.length() == 13 && resultPhoneString.startsWith("+420")) {
            resultPhoneString = resultPhoneString.replaceFirst("\\+", "00");
        }
        if (resultPhoneString.length() == 9) {
            resultPhoneString = String.format("00420%s", resultPhoneString);
        }

        StringBuilder sbBuilder = new StringBuilder();
        char[] result = resultPhoneString.toCharArray();
        int j = 1;
        for (char c : result) {
            sbBuilder.append(c);
            if (j == 5 || j == 8 || j == 11) {
                sbBuilder.append(" ");
            }
            j++;
        }

        return sbBuilder.toString();
    }
}
