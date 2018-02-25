/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.validations;

import java.awt.Checkbox;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

/**
 *
 * @author Polypackaging-A1
 */
public class CheckBoxValidationImpl implements Validator<Boolean> {

    private final Node node1;
    private final Node node2;
    private final Node node3;
    private final Node node4;
    private boolean validated;
    private final String possibleError;

    public CheckBoxValidationImpl(Node checkbox1, Node checkbox2, Node checkbox3, Node textField, String msg) {

        node1 = checkbox1;
        node2 = checkbox2;
        node3 = checkbox3;
        node4 = textField;
        possibleError = msg;

    }

    @Override
    public ValidationResult apply(Control t, Boolean u) {

        CheckBox chb1 = (CheckBox) node1;
        CheckBox chb2 = (CheckBox) node2;
        CheckBox chb3 = (CheckBox) node3;
        TextField textField = (TextField) node4;

        if ((((chb1.isSelected()) == false && (chb2.isSelected()) == false)
                || ((chb1.isSelected()) == false && (chb3.isSelected()) == false)
                || ((chb3.isSelected()) == false && (chb2.isSelected()) == false)) && "Half Board".equalsIgnoreCase(textField.getText())) {

            return ValidationResult.fromErrorIf(t, ErrorMessages.SelectElement, true);

        } else {
            return ValidationResult.fromErrorIf(t,ErrorMessages.ValidEntry,validated);
        }

    }

}
