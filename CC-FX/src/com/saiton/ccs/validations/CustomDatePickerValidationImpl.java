/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saiton.ccs.validations;

import java.time.LocalDate;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

/**
 *
 * @author Polypackaging-A1
 */
public class CustomDatePickerValidationImpl implements Validator<LocalDate> {
       private final Node node;
    private  boolean validated;
    private final String possibleError;
    

    public CustomDatePickerValidationImpl(Node n, boolean validation, String msg) {

        node = n;
        validated = validation;
        possibleError = msg;
        
    }

    @Override
    public ValidationResult apply(Control t, LocalDate u) {

        DatePicker datePicker = (DatePicker) node;
        LocalDate date = datePicker.getValue();
        
         if (date == null){
             
             return ValidationResult.fromErrorIf(t,ErrorMessages.Empty, date == null);
            
        } else if(date !=null ) {
            return ValidationResult.fromErrorIf(t, possibleError ,validated);
//            return ValidationResult.fromErrorIf(t, ErrorMessages.InvalidAgeGroup,!date.isBefore(LocalDate.now().minusYears(6)));
        }else {
            return ValidationResult.fromErrorIf(t, ErrorMessages.Error,true);
        }

    }

  
}

