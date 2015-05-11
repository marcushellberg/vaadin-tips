package org.vaadin.marcus.util;

import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;


public class FieldGroupUtil {

    /**
     * Improves validation UX and enables/disables save/reset buttons depending on FieldGroup state.
     */
    public static void improveUX(FieldGroup fieldGroup, Button saveButton, Button clearButton) {

        saveButton.setEnabled(false);

        if (clearButton != null) {
            clearButton.setEnabled(false);
        }

        Property.ValueChangeListener buttonStateListener = event -> {
            // Only enable clearing if user has changed something
            if (clearButton != null) {
                clearButton.setEnabled(true);
            }
            // Only enable save if the fieldgroup is valid
            saveButton.setEnabled(fieldGroup.isValid());
        };

        fieldGroup.getFields().forEach(field -> {
            AbstractField f = (AbstractField) field;

            // Validate on the fly
            f.setImmediate(true);
            f.addValueChangeListener(buttonStateListener);

            if(!f.getValidators().isEmpty()) {

                // Don't tell the user they've done something wrong if they haven't even tried filling the form yet
                f.setValidationVisible(false);
                f.addValueChangeListener(new Property.ValueChangeListener() {

                    @Override
                    public void valueChange(Property.ValueChangeEvent event) {
                        f.setValidationVisible(true);
                        f.removeValueChangeListener(this);
                    }
                });
            }
        });
    }
}
