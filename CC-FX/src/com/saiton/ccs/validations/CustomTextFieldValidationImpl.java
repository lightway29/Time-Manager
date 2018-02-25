/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.validations;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

/**
 *
 * @author Polypackaging-A1
 */
public class CustomTextFieldValidationImpl implements Validator<String> {

    private final Node node;
    private  boolean validated;
    private final String possibleError;

    public CustomTextFieldValidationImpl(Node n, boolean validation, String msg) {

        node = n;
        validated = validation;
        possibleError = msg;
        
    }

    @Override
    public ValidationResult apply(Control t, String u) {

        TextField textField = (TextField) node;
        
         if (textField.getText().isEmpty()){
             
             return ValidationResult.fromErrorIf(t,ErrorMessages.Empty, textField.getText().isEmpty());
            
        } else if (validated == true) {
            
            return ValidationResult.fromErrorIf(t, possibleError, validated);
             
        } else if (validated == false) {
            return ValidationResult.fromErrorIf(t, ErrorMessages.ValidEntry, validated);
        } else {
            return ValidationResult.fromErrorIf(t, ErrorMessages.Error,true);
        }

    }

}


