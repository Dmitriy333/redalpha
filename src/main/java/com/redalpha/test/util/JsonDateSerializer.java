package com.redalpha.test.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Used to serialize {@link java.util.Date}, which is not a common JSON type, so
 * we have to create a custom serialize method. Date format:
 * "HH:mm:ss" Time zone: UTC
 */
@Component
public class JsonDateSerializer extends JsonSerializer<Date> {

    public static final String DATE_PATTERN = "HH:mm:ss";

    @Override
    public void serialize(Date date, JsonGenerator generator, SerializerProvider provider) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);

        String formattedDate = dateFormat.format(date);
        generator.writeString(formattedDate);
    }
}