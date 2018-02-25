/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.validations;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

public class FormatAndValidate {

    private static FormatAndValidate fav = null;

    public FormatAndValidate() {
    }

    public static FormatAndValidate getInstance() {
        if (fav == null) {
            fav = new FormatAndValidate();
        }
        return fav;
    }

    public boolean validMinInt(String number, int min) {
        try {
            int val = Integer.parseInt(number);
            return val >= min;
        } catch (NumberFormatException ex) {
        }
        return false;
    }

    public String formatdate(String date) {

        String newDate = "";
        String year = "20" + date.split("/")[2];
        String month = date.split("/")[0];
        String pdate = date.split("/")[1];

        int checkMonth = Integer.parseInt(month);
        int checkDate = Integer.parseInt(pdate);

        if (checkMonth < 10) {
            month = "0" + month;
        }

        if (checkDate < 10) {
            pdate = "0" + pdate;
        }

        newDate = year + "-" + month + "-" + pdate;
        return newDate;

    }

    //<editor-fold defaultstate="collapsed" desc="roundTwoDecimals">
    //========================================================================================
    //Formating Methods
    //========================================================================================
//</editor-fold>
    public String roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("0.00");
        twoDForm.setGroupingUsed(false);
        return twoDForm.format(d);
    }

    public String roundTwoDecimals(float d) {
        DecimalFormat twoDForm = new DecimalFormat("0.00");
        twoDForm.setGroupingUsed(false);
        return twoDForm.format(d);
    }

    public String roundThreeDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("0.000");
        twoDForm.setGroupingUsed(false);
        return twoDForm.format(d);
    }

    //<editor-fold defaultstate="collapsed" desc="FormatDecimal">
    //When using prices this method is used to format the price
//</editor-fold>
    public String FormatDecimal(Float d) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        return nf.format(d);
    }

    public String FormatDecimal(double d) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        return nf.format(d);
    }

    //<editor-fold defaultstate="collapsed" desc="isInteger">
    //========================================================================================
    //Validation Methods
//Validate Integer by passing a String    
//========================================================================================
//</editor-fold>
    public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="isFloat">
    //==========================================================================
    //Validate Float by parsing a String 
    //==========================================================================
//</editor-fold>
    public boolean isFloat(String input) {
        try {
            Float.parseFloat(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="isDouble">
    //==========================================================================
    //Validate Double  by parsing a String 
    //==========================================================================
//</editor-fold>
    public boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPriceDouble(String input) {
        try {
            return (Double.parseDouble(input) >= 0.0);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isServiceDouble(String input) {
        try {
            double service = Double.parseDouble(input);
            return ((service >= 0.0) && (service <= 100.0));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAmountIDouble(String input) {
        try {
            double amount = Double.parseDouble(input);
            return amount >= 0;
        } catch (Exception e) {
            return false;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="checkPhone">
    //==========================================================================
    //Validating Phone Numbers
    //==========================================================================
//</editor-fold>
    public boolean checkPhone(String phoneNo) {

        boolean accept = false;
        String check = "^\\(?(\\d{3})\\)?[- ]?(\\d{7})$";

        CharSequence phone = phoneNo;

        Pattern p = Pattern.compile(check);
        Matcher m = p.matcher(phone);

        if (m.matches()) {

            accept = true;
        }

        return accept;

    }

    //<editor-fold defaultstate="collapsed" desc="chkPrice">
    //==========================================================================
    //Validating Price
    //==========================================================================
//</editor-fold>
    public boolean chkPrice(String price) {

        boolean valid = false;

        String expression = "[0-9]*\\.?[0-9]?[0-9]?$";
        CharSequence inputStr = price;

        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            valid = true;
        }
        return valid;
    }

    //<editor-fold defaultstate="collapsed" desc="checkEmail">
    //========================================================================================
    //validate E-mail
    //========================================================================================
//</editor-fold>
    public boolean checkEmail(String email) {

        boolean valid = false;
        String expression
                = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            valid = true;
        }
        return valid;
    }

    //<editor-fold defaultstate="collapsed" desc="checkNIC">
    //========================================================================================
    //validate NIC by parsing a string
    //========================================================================================
//</editor-fold>
    public boolean checkNIC(String nic) {

        boolean accept = false;
        String check = "^\\(?(\\d{9})\\)?[VX]$";

        CharSequence phone = nic;

        Pattern p = Pattern.compile(check);
        Matcher m = p.matcher(phone);

        if (m.matches()) {

            accept = true;
        }

        return accept;

    }

    public boolean checkNBTVATSVAT(String tax) {

        boolean accept = false;
        String check = "\\d{9}?[-]\\d{4}?";

        CharSequence vat = tax;

        Pattern p = Pattern.compile(check);
        Matcher m = p.matcher(vat);

        if (m.matches()) {

            accept = true;
        }

        return accept;

    }

    //<editor-fold defaultstate="collapsed" desc="validNic">
    //========================================================================================
    //validate NIC by parameter a string
    //========================================================================================
//</editor-fold>
    public boolean validNic(String nic) {
        boolean valid = false;

        String regex_nic
                = "([0-9])([0-9])([0-9][0-9])([0-9])([0-9][0-9])([0-9])([0-9]+)([V v])";  //Regex pattern for NIC with V.

        Pattern pattern_nic = Pattern.compile(regex_nic);   //    Pattern Compliler

        Matcher matcher_NIC = pattern_nic.matcher(nic);   // Pattern Matcher where the  NIC with V argument is checked with the given pattern

        boolean matched_NIC = matcher_NIC.matches();       // Result of the matcher is returned as a boolean for NIC

        if (nic.length() > 10 | matched_NIC == false | nic.length() < 10) {

            valid = false;

        } else if (!nic.isEmpty() & matched_NIC == true) {

            valid = true;
        }

        return valid;

    }

    public static Date stringToDate(String dat) throws ParseException {

        SimpleDateFormat dateFormat
                = new SimpleDateFormat("yyyy-MM-dd hh:mm aa", Locale.US);
        Date date = dateFormat.parse(dat);
        return date;

    }

    private static boolean doesTheDateFallsBetween(Date from, Date to, Date date) {

        return (date.equals(from) || date.equals(to) || (date.after(from)
                && date.before(to)));

    }

    public static boolean doesDatesOverlap(Date toCheckFrom, Date toCheckTo,
            Date from, Date to) {
        if (toCheckFrom == null || toCheckTo == null || from == null || to
                == null) {
            return false;
        } else {
            return (doesTheDateFallsBetween(from, to, toCheckFrom)
                    || doesTheDateFallsBetween(from, to, toCheckTo));
        }
    }

    public static boolean doesDate1GraterThanDate2(Date toCheckFrom,
            Date toCheckTo) {
        if (toCheckFrom == null || toCheckTo == null) {
            return false;
        } else {
            if (toCheckFrom.after(toCheckTo)) {
                return false;
            } else {
                return true;
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="validName">
    //========================================================================================
    //validate Name by parameter a string
    //========================================================================================
//</editor-fold>
    public boolean validName(String name) {
        boolean valid = false;
        try {
            if (name.length() > 0 && name.length() < 300 && !name.isEmpty()) {

                valid = true;

            } else {

                valid = false;
            }
        } catch (Exception e) {
            return false;
        }

        return valid;

    }

    //<editor-fold defaultstate="collapsed" desc="validPassport">
    //========================================================================================
    //validate Name by parameter a string
    //========================================================================================
//</editor-fold>
    public boolean validPassport(String passport) {
        boolean valid = false;

        if (passport.length() > 1 && !passport.isEmpty() && passport.length()
                < 300) {

            valid = true;
        } else {

            valid = false;

        }

        return valid;
    }

    //<editor-fold defaultstate="collapsed" desc="validProfession">
    //========================================================================================
    //validate Profession.parameter a string
    //========================================================================================
//</editor-fold>
    public boolean validProfession(String profession) {

        boolean valid = false;

        String regex_single_name = "([a-zA-Z&&[^0-9]])*";
        String regex_multiple_name
                = "((([a-zA-Z&&[^0-9]])*+\\s+([a-zA-Z&&[^0-9]])*)+)*";

        Pattern pattern_multiple_name = Pattern.compile(regex_multiple_name);
        Pattern pattern_single_name = Pattern.compile(regex_single_name);

        if (profession.length() > 1 && !profession.isEmpty() && profession.
                length() < 300) {

            Matcher matcher_multiple_name = pattern_multiple_name.matcher(
                    profession);
            Matcher matcher_single_name = pattern_single_name.
                    matcher(profession);

            boolean matched_multiple_name = matcher_multiple_name.matches();
            boolean matched_single_name = matcher_single_name.matches();

            if (matched_single_name == true || matched_multiple_name == true) {
                valid = true;
            } else if (matched_single_name == false && matched_multiple_name
                    == false) {
                valid = false;
            }

        } else {

            valid = false;

        }

        return valid;
    }

    //<editor-fold defaultstate="collapsed" desc="validAddress">
    //========================================================================================
    //validate Name by parameter a string
    //========================================================================================
//</editor-fold>
    public boolean validAddress(String address) {
        boolean valid = false;

        if (address.length() > 1 && address.length() < 300 && !address.isEmpty()) {

            valid = true;

        } else {

            valid = false;
        }

        return valid;

    }

    //<editor-fold defaultstate="collapsed" desc="validTelephoneNumber">
    //========================================================================================
    //validate Telephone Number by parameter a String
    //========================================================================================
//</editor-fold>
    public boolean validTelephoneNumber(String telephone) {
        boolean valid = false;

        String telephoneNumber = telephone.trim();

        String regex_Number = "([0-9])*";     //Regex pattern for Single Name with initials.

        Pattern pattern_Number = Pattern.compile(regex_Number);   //    Pattern Compliler

        Matcher matcher_Number = pattern_Number.matcher(telephoneNumber);   // Pattern Matcher where the Single Name argument is checked with the given pattern

        boolean matched_Number = matcher_Number.matches();       // Result of the matcher is returned as a boolean for Single Name

        if (matched_Number == true && telephoneNumber.length() < 14
                && telephoneNumber.length() > 9) {

            valid = true;

        } else {

            valid = false;
        }

        return valid;

    }

    //<editor-fold defaultstate="collapsed" desc="validEmail">
    //========================================================================================
    //validate Email address by parameter a String
    //========================================================================================
//</editor-fold>
    public boolean validEmail(String email) {

        return org.apache.commons.validator.routines.EmailValidator.
                getInstance().isValid(email);

    }

    //<editor-fold defaultstate="collapsed" desc="validListView">
    //========================================================================================
    //validate list view by parameter a listview
    //========================================================================================
//</editor-fold>
    public boolean validListView(ListView listView) {
        boolean valid = false;

        if (!listView.getItems().isEmpty()) {
            valid = true;
        } else {

            valid = false;
        }
        return valid;

    }

    //<editor-fold defaultstate="collapsed" desc="validNumber">
    //========================================================================================
    //validate Number by parameter a String
    //========================================================================================
//</editor-fold>
    public boolean validNumber(String No) {
        boolean matched_Number = false;
        String number = No.trim();

        String regex_Number = "([0-9])*";     //Regex pattern for Single Name with initials.

        Pattern pattern_Number = Pattern.compile(regex_Number);   //    Pattern Compliler

        Matcher matcher_Number = pattern_Number.matcher(number);   // Pattern Matcher where the Single Name argument is checked with the given pattern

        matched_Number = matcher_Number.matches();       // Result of the matcher is returned as a boolean for Single Name

        return matched_Number;

    }

    //<editor-fold defaultstate="collapsed" desc="validQty">
    //========================================================================================
    //validate validQty by set parameter a String
    //========================================================================================
//</editor-fold>
    public boolean validQty(String No, Integer qty) {
        boolean matched_Number = false;
        String number = No.trim();

        String regex_Number = "([0-9])*";     //Regex pattern for Single Name with initials.

        Pattern pattern_Number = Pattern.compile(regex_Number);   //    Pattern Compliler

        Matcher matcher_Number = pattern_Number.matcher(number);   // Pattern Matcher where the Single Name argument is checked with the given pattern

        matched_Number = matcher_Number.matches();       // Result of the matcher is returned as a boolean for Single Name
        try {
            return matched_Number && qty >= Integer.parseInt(No);
        } catch (Exception e) {
            return false;
        }

    }
    
    //<editor-fold defaultstate="collapsed" desc="validQty">
    //========================================================================================
    //validate validQty by set parameter a String
    //========================================================================================
//</editor-fold>
    public boolean validQty(String No, Double qty) {
        boolean matched_Number = false;
        String number = No.trim();

        String regex_Number = "[0-9]*\\.?[0-9]?[0-9]";     //Regex pattern for Single Name with initials.

        Pattern pattern_Number = Pattern.compile(regex_Number);   //    Pattern Compliler

        Matcher matcher_Number = pattern_Number.matcher(number);   // Pattern Matcher where the Single Name argument is checked with the given pattern

        matched_Number = matcher_Number.matches();       // Result of the matcher is returned as a boolean for Single Name
        try {
            return matched_Number && qty >= Double.parseDouble(No);
        } catch (Exception e) {
            return false;
        }

    }
    
    //<editor-fold defaultstate="collapsed" desc="overlapNumber">
    //========================================================================================
    //validate Overlap Number by parameter a String
    //========================================================================================
//</editor-fold>
    public boolean overlapNumber(String No1, String No2) {
        String number1 = No1.trim();
        String number2 = No2.trim();

        if (Integer.parseInt(number1) < Integer.parseInt(number2)) {
            return true;
        } else {
            return false;
        }

    }

    //<editor-fold defaultstate="collapsed" desc="validCombobox">
    //========================================================================================
    //validate combobox list view by parameter a combobox
    //========================================================================================
//</editor-fold>
    public boolean validCombobox(ComboBox combo) {
        boolean valid = false;

        if (!combo.getItems().isEmpty()) {
            valid = true;
        } else {

            valid = false;
        }
        return valid;

    }

    //<editor-fold defaultstate="collapsed" desc="validHallName">
    //========================================================================================
    //validate Profession.parameter a string
    //========================================================================================
//</editor-fold>
    public boolean validHallName(String hallName) {

        boolean valid = false;
        try {
            if (!hallName.isEmpty()) {

                String regex_single_name = "[a-zA-Z]*";     //Regex pattern for Single Name 
                String regex_multiple_name = "((([a-zA-Z])*+\\s+([a-zA-Z])*)+)*";      //Regex pattern for Multiple Names with initials.

                Pattern pattern_single_name = Pattern.compile(regex_single_name);   //    Pattern Compliler
                Pattern pattern_multiple_name = Pattern.compile(
                        regex_multiple_name);  //    Pattern Compliler

                Matcher matcher_single_name = pattern_single_name.matcher(
                        hallName.trim());   // Pattern Matcher where the Single Name argument is checked with the given pattern
                Matcher matcher_multiple_name = pattern_multiple_name.matcher(
                        hallName.trim());  // Pattern Matcher where the Multiple Names argument is checked with the given pattern

                boolean matched_single_name = matcher_single_name.matches();       // Result of the matcher is returned as a boolean for Single Name
                boolean matched_multiple_name = matcher_multiple_name.matches();      // Result of the matcher is returned as a boolean for Multiple Names

                if (matched_single_name == true || matched_multiple_name == true) {
                    valid = true;
                } else if (matched_single_name == false && matched_multiple_name
                        == false) {
                    valid = false;
                }

            } else {

                valid = false;
            }
        } catch (Exception e) {
            return false;
        }

        return valid;
    }

    //<editor-fold defaultstate="collapsed" desc="validTableView">
    //========================================================================================
    //validate list view by parameter a Tableview
    //========================================================================================
//</editor-fold>
    public boolean validTableView(TableView tableView) {
        boolean valid = false;

        if (!tableView.getItems().isEmpty()) {
            valid = true;
        } else {

            valid = false;
        }
        return valid;

    }

    //<editor-fold defaultstate="collapsed" desc="isListViewItemDuplicated">
    //========================================================================================
    //validate listview item duplication by parameters listview,value
    //========================================================================================
//</editor-fold>
    public boolean isListViewItemDuplicated(ListView listView, String value) {

        boolean valid = false;
        ObservableList<String> list = listView.getItems();

        if (list.size() != 0 && !value.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {

                if (!list.get(i).equals(value)) {
                    valid = true;

                }
            }

        } else if (list.size() == 0) {
            valid = true;
        }
        return valid;
    }

    //<editor-fold defaultstate="collapsed" desc="isValidUniqueTelephoneNumber">
    //========================================================================================
    //validate Telephone Number by parameter a String
    //========================================================================================
//</editor-fold>
    public boolean isValidUniqueTelephoneNumber(ListView listView,
            String telephone) {
        boolean valid = true;

        String telephoneNumber = telephone.trim();
        ObservableList<String> list = listView.getItems();
        

        String regex_Number = "([0-9])*";     //Regex pattern for Single Name with initials.

        Pattern pattern_Number = Pattern.compile(regex_Number);   //    Pattern Compliler

        Matcher matcher_Number = pattern_Number.matcher(telephoneNumber);   // Pattern Matcher where the Single Name argument is checked with the given pattern

        boolean matched_Number = matcher_Number.matches();       // Result of the matcher is returned as a boolean for Single Name

        if (matched_Number == true && telephoneNumber.length() < 14
                && telephoneNumber.length() > 9) {
            
            if (list.size() != 0 && !telephone.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                   
                    if (list.get(i).equals(telephone)) {
                        valid = false;
                        
                    } 
//                    else {
//
//                        valid = false;
//
//                    }
                }
            } else if (list.size() == 0) {
                valid = true;
            }

        } else {

            valid = false;
        }

        return valid;

    }

    public boolean isValidUsername(String name) {
        Pattern p = Pattern.compile("[a-zA-Z]+");
        return (name.length() >= 3 && p.matcher(name).matches());

    }

    //<editor-fold defaultstate="collapsed" desc="validUniqueEmail">
    //========================================================================================
    //validate and unique Email address by parameter a listViewString
    //========================================================================================
//</editor-fold>
    public boolean isValidUniqueEmail(ListView listView, String email) {

         boolean valid = true;
        ObservableList<String> list = listView.getItems();
        boolean isEmail = org.apache.commons.validator.routines.EmailValidator.
                getInstance().isValid(email);

        if (isEmail == true) {
            if (list.size() != 0 && !email.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {

                    if (list.get(i).equals(email)) {
                        valid = false;
                    }
//                    else {
//                        valid = false;
//                    }

                }
            } else if (list.size() == 0) {
                valid = true;
            }
        }

        return valid;

    }

    //<editor-fold defaultstate="collapsed" desc="validPositiveDoubleAmount">
    //========================================================================================
    //validate PositiveDoubleAmount for payments.Parameter a amount in string.
    //========================================================================================
//</editor-fold>
    public boolean validPositiveDoubleAmount(String amount) {
        boolean valid = false;
        double value = 0.0;
        try {
            value = Double.parseDouble(amount);
            if (value >= 0) {
                valid = true;
            }

        } catch (NumberFormatException e) {

            return false;
        }

        return valid;

    }

    public boolean validNonZeroPositiveDouble(String amount) {
        boolean valid = false;
        double value = 0.0;
        try {
            value = Double.parseDouble(amount);
            if (value > 0) {
                valid = true;
            }

        } catch (NumberFormatException e) {

            return false;
        }

        return valid;

    }
    
    public boolean validDiscountPercentage(String amount) {
        boolean valid = false;
        double value = 0.0;
        try {
            value = Double.parseDouble(amount);
            if (value >= 0 && value <=100) {
                valid = true;
            }

        } catch (NumberFormatException e) {

            return false;
        }

        return valid;

    }
    
    //<editor-fold defaultstate="collapsed" desc="validNumber">
    //========================================================================================
    //validate Number by parameter a String
    //========================================================================================
//</editor-fold>
    public boolean validNumberWithoutSpace(String No) {
        boolean matched_Number = false;
        String number = No;

        String regex_Number = "([0-9])*";     //Regex pattern for Single Name with initials.

        Pattern pattern_Number = Pattern.compile(regex_Number);   //    Pattern Compliler

        Matcher matcher_Number = pattern_Number.matcher(number);   // Pattern Matcher where the Single Name argument is checked with the given pattern

        matched_Number = matcher_Number.matches();       // Result of the matcher is returned as a boolean for Single Name

        return matched_Number;

    }
    
    //<editor-fold defaultstate="collapsed" desc="validTaxInvoiceNo">
    //========================================================================================
    //validate Invoice Number for Tax
    //========================================================================================
//</editor-fold>
    public boolean checkTaxInvoiceNo(String inv) {

        boolean accept = false;
        String check = "([a-zA-Z])([a-zA-Z])([T t])";

        CharSequence phone = inv;

        Pattern p = Pattern.compile(check);
        Matcher m = p.matcher(phone);

        if (m.matches()) {

            accept = true;
        }

        return accept;
    }
    
    //<editor-fold defaultstate="collapsed" desc="validNonTaxInvoiceNo">
    //========================================================================================
    //validate Invoice Number for NonTax
    //========================================================================================
//</editor-fold>
    public boolean checkNonTaxInvoiceNo(String inv) {

        boolean accept = false;
        String check = "([a-zA-Z])([a-zA-Z])([V v])";

        CharSequence phone = inv;

        Pattern p = Pattern.compile(check);
        Matcher m = p.matcher(phone);

        if (m.matches()) {

            accept = true;
        }

        return accept;
    }
    
    //<editor-fold defaultstate="collapsed" desc="isValidVehicleNo">
    //========================================================================================
    //validate Vehicle Number by parameter a String
    //========================================================================================
//</editor-fold>
    public boolean isValidVehicleNo(ListView listView,
            String vehicleNo) {
        boolean valid = true;

        String vehicleNoTrimed = vehicleNo.trim();
        ObservableList<String> list = listView.getItems();
        
      
       

        if ( vehicleNoTrimed.length() < 14
                && vehicleNoTrimed.length() > 4) {
            
            if (list.size() != 0 && !vehicleNo.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                   
                    if (list.get(i).equals(vehicleNo)) {
                        valid = false;
                        
                    } 
//                    else {
//
//                        valid = false;
//
//                    }
                }
            } else if (list.size() == 0) {
                valid = true;
            }

        } else {

            valid = false;
        }

        return valid;

    }

}
