package com.redalpha.test.service;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.redalpha.test.model.Call;

/**
 * Contains methods to validate call's fields.
 */
@Service
public class CallValidationService {

    public static final int MAX_LENGTH = 30;
    private static final Pattern PHONE_ALLOWED_CHARACTERS_PATTERN = Pattern
            .compile("\\+?((00)?)((420)?)[\\s-]?\\(?([0-9]{3})\\)?([\\s-]?)([0-9]{3})([\\s-]?)\\(?([0-9]{3})\\)?");
    // .compile("(\\+|(\\(?420\\)?))?(-|\\s)?\\(?[0-9]{3}\\)?\\s?[0-9]{3}\\(?\\[0-9]{3}\\)?");

    public void validateCall(Call call) {
        if (call == null) {
            throw new EntityValidationException("Call can not be empty.");
        }
        validatePhone(call.getPhone());
        validateLastname(call.getLastname());
        validateFirstname(call.getFirstname());
    }

    public void validateFirstname(String firstname) {
        if (firstname != null && firstname.length() > MAX_LENGTH) {
            throw new EntityValidationException(
                    String.format("Call's lastname length can not be more than %s characters.", MAX_LENGTH));
        }
    }

    public void validateLastname(String lastname) {
        if (StringUtils.isEmpty(lastname)) {
            throw new EntityValidationException("Call's lastname can not be empty.");
        }

        if (lastname.length() > MAX_LENGTH) {
            throw new EntityValidationException(
                    String.format("Call's lastname length can not be more than %s characters.", MAX_LENGTH));
        }
    }

    public void validatePhone(String phone) {

        if (StringUtils.isEmpty(phone)) {
            throw new EntityValidationException("Phone can not be empty.");
        }

        if (!(phone.matches("\\d{9}") || phone.matches("\\(\\d{3}\\)\\s\\d{3}\\s\\(\\d{3}\\)")
                || phone.matches("00420\\s\\d{9}") || phone.matches("\\+420\\d{9}")
                || phone.matches("\\+\\(420\\)-\\d{9}") || phone.matches("\\+\\(420\\)\\s\\d{3}\\s\\d{3}\\s\\d{3}"))) {

            throw new EntityValidationException("Phone contains invalid characters.");
        }
    }
}
