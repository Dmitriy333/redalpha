package com.redalpha.test.util.phone;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PhoneParserTest {

    private PhoneParser phoneParser = new PhoneParser();
    private static final String EXPECTED_PHONE = "00420 111 222 333";
    private static final List<String> TEST_PHONES = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add("+(420) 111 222 333");
            add("+(420)-111222333");
            add("+420111222333");
            add("00420111222333");
            add("(111) 222 (333)");
            add("111222333");
        }
    };

    @Test
    public void parsePhoneTest() {
        List<String> parsedPhones = new ArrayList<String>();
        for (String phone : TEST_PHONES) {
            parsedPhones.add(phoneParser.parsePhone(phone));
        }
        for (String parsedPhone : parsedPhones) {
            assertEquals(parsedPhone, EXPECTED_PHONE);
        }
        assertEquals(parsedPhones.size(), 6);
    }
}
