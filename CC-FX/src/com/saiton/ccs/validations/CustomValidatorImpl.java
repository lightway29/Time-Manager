/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saiton.ccs.validations;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

/**
 *
 * @author Polypackaging-A1
 */
public class CustomValidatorImpl implements Validator<String> {
    
    
       private final Node node;
        private final boolean validated;
        private final String possibleError;
        private final String currentCase;
        public CustomValidatorImpl(Node n , boolean v, String msg , String currentcase) {
            node = n;
            validated = v;
           possibleError = msg;
           currentCase = currentcase;
        }

        @Override
        public ValidationResult apply(Control t, String u) {
            

            ComboBox<String> cmBox = (ComboBox<String>) node;
            if(cmBox.getValue().equals("NIC")){

                    
                    
                
                
                    return ValidationResult.fromErrorIf(t, possibleError,validated);
                
                
            }else{
                
                return ValidationResult.fromErrorIf(t, "Buwahaha", true);
                
            }
        }
        
 
    
}
