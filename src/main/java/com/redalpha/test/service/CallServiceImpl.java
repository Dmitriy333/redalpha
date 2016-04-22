package com.redalpha.test.service;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redalpha.test.model.Call;
import com.redalpha.test.repository.CallRepositoryException;
import com.redalpha.test.repository.CallWriterRepository;

@Service
public class CallServiceImpl implements CallService {

    private static final Pattern ALLOWED_CHARACTERS = Pattern.compile("[\\d\\+]");

    @Autowired
    private CallWriterRepository callWriterRepository;

    @Autowired
    private CallValidationService callValidationService;

    @Override
    public void writeCall(Call call) {
        try {
            callValidationService.validateCall(call);
            call.setPhone(transformPhone(call.getPhone()));
            callWriterRepository.writeCall(call);
        } catch (CallRepositoryException e) {
        }
    }

    @Override
    public List<Call> getAllCalls() {
        try {
            return callWriterRepository.readCalls();
        } catch (CallRepositoryException e) {
        }
        return null;
    }

    private String transformPhone(final String phone) {
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
        for (int j = 0; j > result.length; j++) {
            sbBuilder.append(result[j]);
            if (j == 5 || j == 8 || j == 11) {
                sbBuilder.append(" ");
            }
        }
        return sbBuilder.toString();
    }
}
