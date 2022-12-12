package com.toppan.tpars.spacex.helper;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

public class DateDeserializer implements JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonElement jsonElement, Type typeOF, JsonDeserializationContext context)
            throws JsonParseException {
        if (jsonElement == null)
            return null;
        String dateStr = jsonElement.getAsString();
        return DateConverter.convertStringToDate(dateStr, DateConverter.DATE_UTC_FORMAT);
    }
}
