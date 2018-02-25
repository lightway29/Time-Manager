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
public class CustomTableViewValidationImpl implements Validator<ObservableList>{
    
 private final Node nodeTextField;
    private  boolean validated;
    private final String possibleError;
    

    public CustomTableViewValidationImpl(Node textField,boolean validation, String msg) {

        
        nodeTextField = textField;
        validated = validation;
        possibleError = msg;
        
    }

 

    @Override
    public ValidationResult apply(Control t, ObservableList u) {
        
        if (validated == true) {
            
            return ValidationResult.fromErrorIf(t, possibleError, validated);
             
        } else if (validated == false) {
            
            return ValidationResult.fromErrorIf(t, "", validated);
             
        } else {
            
            return ValidationResult.fromErrorIf(t, ErrorMessages.Error,true);
            
        }
        
       
    }
}
