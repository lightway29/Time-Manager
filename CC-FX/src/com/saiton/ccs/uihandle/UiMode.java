/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.uihandle;

/**
 *
 * @author Polypackaging-A1
 */
public enum UiMode {

    SAVE("save"), DELETE("delete"), UPDATE("update"), FULL_ACCESS("fullAccess"),
    READ_ONLY("readOnly"),
    ALL_BUT_DELETE("allButDelete"),
    NO_ACCESS("noAccess");
    private final String val;

    private UiMode(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return val;
    }

}
