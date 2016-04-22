package com.redalpha.test.service.call;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.redalpha.test.model.call.Call;
import com.redalpha.test.service.EntityValidationException;

/**
 * Contains methods to validate call's fields.
 */
@Service
public class CallValidationService {

    public static final int MAX_LENGTH = 30;
    private static final Pattern PHONE_ALLOWED_CHARACTERS_PATTERN = Pattern.compile("(\\+420)?(00420)?[\\s-]?\\(?\\d{3}\\)?\\s?\\d{3}\\s?\\(?\\d{3}\\)?");

    public void validateCall(Call call) throws EntityValidationException {
        if (call == null) {
            throw new EntityValidationException("Call can not be empty.");
        }
        validatePhone(call.getPhone());
        validateLastname(call.getLastname());
        validateFirstname(call.getFirstname());
    }

    public void validateFirstname(String firstname) throws EntityValidationException {
        if (firstname != null && firstname.length() > MAX_LENGTH) {
            throw new EntityValidationException(
                    String.format("Call's lastname length can not be more than %s characters.", MAX_LENGTH));
        }
    }

    public void validateLastname(String lastname) throws EntityValidationException {
        if (StringUtils.isEmpty(lastname)) {
            throw new EntityValidationException("Call's lastname can not be empty.");
        }

        if (lastname.length() > MAX_LENGTH) {
            throw new EntityValidationException(
                    String.format("Call's lastname length can not be more than %s characters.", MAX_LENGTH));
        }
    }

    public void validatePhone(String phone) throws EntityValidationException {
        if (StringUtils.isEmpty(phone)) {
            throw new EntityValidationException("Phone can not be empty.");
        }

        if (!(PHONE_ALLOWED_CHARACTERS_PATTERN.matcher(phone).matches())) {
            throw new EntityValidationException("Phone contains invalid characters or has unsupported format");
        }
        int numberOfOpenedBrackets = 0;
        int numberOfClosedBrackets = 0;
        for (char character : phone.toCharArray()) {
            if (character == '(') {
                numberOfOpenedBrackets++;
            }
            if (character == ')') {
                numberOfClosedBrackets++;
            }
        }
        if (numberOfOpenedBrackets != numberOfClosedBrackets) {
            throw new EntityValidationException("Check your brackets in phone.");
        }
    }
}