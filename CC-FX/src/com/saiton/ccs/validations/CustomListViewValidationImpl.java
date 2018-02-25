/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.validations;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

/**
 *
 * @author Polypackaging-A1
 */
public class CustomListViewValidationImpl implements Validator<ObservableList> {

    private final Node NODE;
    private boolean validated;
    private final String possibleError;

    public CustomListViewValidationImpl(Node node, boolean validation, String msg) {

        NODE = node;
        validated = validation;
        possibleError = msg;

    }

    @Override
    public ValidationResult apply(Control t, ObservableList u) {

        if (u.isEmpty()) {
            return ValidationResult.fromErrorIf(t, ErrorMessages.Empty, true);
            
        } else if (validated == true) {

            return ValidationResult.fromErrorIf(t, possibleError, validated);

        } else if (validated == false) {

            return ValidationResult.fromErrorIf(t, "", validated);

        } else {

            return ValidationResult.fromErrorIf(t, ErrorMessages.Error, true);

        }

    }

    /* @Override
     public ValidationResult apply(Control t, ObservableList u) {
        
     if (validated == true) {
            
     return ValidationResult.fromErrorIf(t, possibleError, validated);
             
     } else if (validated == false) {
            
     return ValidationResult.fromErrorIf(t, "", validated);
             
     } else {
            
     return ValidationResult.fromErrorIf(t, ErrorMessages.Error,true);
            
     }
        
       
     }*/
}
