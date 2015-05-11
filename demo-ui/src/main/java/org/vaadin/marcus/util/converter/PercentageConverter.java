package org.vaadin.marcus.util.converter;

import com.vaadin.data.util.converter.Converter;

import java.util.Locale;

public class PercentageConverter implements Converter<String, Double> {
    @Override
    public Double convertToModel(String value, Class<? extends Double> targetType, Locale locale) throws ConversionException {
        double val = 0.0;
        try {
            val = new Double(value.replaceAll("[^\\d.]", "")) / 100;
        } catch (NumberFormatException nex) {
            // ignore invalid input
        }
        return val;
    }

    @Override
    public String convertToPresentation(Double value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        return String.valueOf(value * 100) + "%";
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
