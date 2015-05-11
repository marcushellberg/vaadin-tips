package org.vaadin.marcus.util;

import com.vaadin.data.validator.AbstractValidator;

import java.util.Collection;

/**
 * Setting a field as required will not prevent empty lists from being submitted.
 */
public class NonEmptyCollectionValidator extends AbstractValidator<Collection> {

    public NonEmptyCollectionValidator(String errorMessage) {
        super(errorMessage);
    }

    @Override
    protected boolean isValidValue(Collection value) {
        return value != null && !value.isEmpty();
    }

    @Override
    public Class<Collection> getType() {
        return Collection.class;
    }
}
