/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author sumudu
 */
public class TimeConvert {

    // Replace with KK:mma if you want 0-11 interval
    private static final DateFormat TWELVE_TF = new SimpleDateFormat("hh:mma");
    // Replace with kk:mm if you want 1-24 interval
    private static final DateFormat TWENTY_FOUR_TF = new SimpleDateFormat(
            "HH:mm");

    public static String convertTo24HoursFormat(String twelveHourTime) {
        try {
            return TWENTY_FOUR_TF.format(
                    TWELVE_TF.parse(twelveHourTime));
        } catch (ParseException ex) {
            return null;
        }
    }

    public static String convertTo12HoursFormat(String twentyFourHours) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            final Date dateObj = sdf.parse(twentyFourHours);

            return (new SimpleDateFormat("hh:mm").format(dateObj));

        } catch (ParseException ex) {
            return null;
        }

    }
}
