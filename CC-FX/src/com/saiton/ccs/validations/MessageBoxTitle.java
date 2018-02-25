/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.validations;

/**
 *
 * @author Shashi De Silva
 */
public enum MessageBoxTitle {
    
    ERROR("Error"),
    INFORMATION("Information"),
    SUCCESS("Success");
    
    private final String val;

    private MessageBoxTitle(String val) {

        this.val = val;
    }

    @Override
    public String toString() {
        return val.toString();
    }

    public static MessageBoxTitle fromString(String val) {
        if (val != null) {
            for (MessageBoxTitle b : MessageBoxTitle.values()) {
                if (val.equalsIgnoreCase(b.val)) {

                    return b;
                }
            }
        }
        return null;
    }
    
}
