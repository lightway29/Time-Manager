package com.saiton.ccs.validations;

/**
 * This interface will be implemented on a validated UI
 * @author Saiton
 */
public interface Validatable {
    /**
     * validate the UI
     * @return is input valid 
     */
    boolean isValid();
    
    /**
     * clear input
     */
    void clearInput();
    
    /**
     * clear special validation styles
     */
    void clearValidations();
}
