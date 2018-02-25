package com.saiton.ccs.validations;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

public class CustomComboboxValidationImplT<T> implements Validator<T> {

    private final Node node;
    private boolean validated;
    private final String possibleError;

    public CustomComboboxValidationImplT(Node n, boolean validation, String msg) {

        node = n;
        validated = validation;
        possibleError = msg;

    }

    @Override
    public ValidationResult apply(Control t, T u) {

        ComboBox combo = (ComboBox) node;

        if (validated == false) {

            return ValidationResult.fromErrorIf(t, possibleError, validated);

        } else if (validated == true) {

            return ValidationResult.fromErrorIf(t, ErrorMessages.ValidEntry,
                    validated);

        } else {

            return ValidationResult.fromErrorIf(t, ErrorMessages.Error, true);

        }

    }
}
