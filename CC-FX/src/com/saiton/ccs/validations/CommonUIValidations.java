package com.saiton.ccs.validations;

import javafx.scene.control.TextField;

/**
 * Common UI Validations
 *
 * @author Saiton
 */
public final class CommonUIValidations {

    private static boolean isNotEmpty(TextField tf) {
        return (tf != null && tf.getText() != null && !tf.getText().equals(""));
    }

    public static boolean validateNotEmpty(TextField tf) {
        boolean isValid = isNotEmpty(tf);
        if (isValid) {
            tf.getStyleClass().remove("error");
        } else {
            tf.getStyleClass().add("error");
        }
        return isValid;
    }

    private static boolean isSimpleNumber(TextField tf) {
        return (isNotEmpty(tf) && tf.getText().matches("[0-9]+"));
    }

    public static boolean validateSimpleNumber(TextField tf) {
        boolean isValid = isSimpleNumber(tf);
        if (isValid) {
            tf.getStyleClass().remove("error");
        } else {
            tf.getStyleClass().add("error");
        }
        return isValid;
    }
}
