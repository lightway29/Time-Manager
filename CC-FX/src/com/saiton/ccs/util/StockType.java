/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.util;

/**
 *
 * @author Polypackaging-A1
 */
public enum StockType {

    BANQUET("banquet"), RESERVATION("reservation"), ALA_CARTE("ala carte");
    private final String val;

    private StockType(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return val;
    }
}
