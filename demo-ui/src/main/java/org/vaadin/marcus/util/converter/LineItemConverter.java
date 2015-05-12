package org.vaadin.marcus.util.converter;

import com.vaadin.data.util.converter.Converter;
import org.vaadin.marcus.entity.LineItem;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class LineItemConverter implements Converter<String, List> {
    @Override
    public List convertToModel(String value, Class<? extends List> targetType, Locale locale) throws ConversionException {
        return null;
    }

    @Override
    public String convertToPresentation(List value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        return String.join(", ", ((List<LineItem>)value)
                .stream()
                .map(LineItem::toString)
                .collect(Collectors.toList()));
    }

    @Override
    public Class<List> getModelType() {
        return List.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
