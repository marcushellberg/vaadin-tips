package org.vaadin.marcus.util.converter;

import com.vaadin.data.util.converter.Converter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

public class DateTimeConverter implements Converter<String, DateTime> {
    DateTimeFormatter format = DateTimeFormat.mediumDateTime();

    @Override
    public DateTime convertToModel(String value, Class<? extends DateTime> targetType, Locale locale) throws ConversionException {
        return null;
    }

    @Override
    public String convertToPresentation(DateTime value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        return format.print(value);
    }

    @Override
    public Class<DateTime> getModelType() {
        return DateTime.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
