package org.vaadin.marcus.util.converter;

import com.vaadin.data.util.converter.Converter;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyConverter implements Converter<String, Double> {
    NumberFormat formatter = NumberFormat.getCurrencyInstance();

    @Override
    public Double convertToModel(String value, Class<? extends Double> targetType, Locale locale) throws ConversionException {
        return null;
    }

    @Override
    public String convertToPresentation(Double value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        return formatter.format(value);
    }

    @Override
    public Class<Double> getModelType() {
        return Double.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
