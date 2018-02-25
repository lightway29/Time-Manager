/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.printerservice;

/**
 *
 * @author Polypackaging-A1
 */
public enum ReportType {
    
    BANQUET_KOT_REPORT("Banquet Kot Report"),
    BANQUET_BOT_REPORT("Banquet Bot Report"),   
    
    
    ADVANCE_RECEIPT("Advance Receipt"),
    BANQUET_MAIN_BILL("Banquet Main Bill"),   
    ALACARTE_BOT_REPORT("Alacarte BOT Report"),
    
    ROOM_BOT_REPORT("Room BOT Report"),
    EXTERNAL_GOOD_RECEIVED_NOTE("External Good Received Note"),
    FUNCTION_SHEET_MAIN("Function Sheet Main"),
    FUNCTION_SHEET_SUBREPORT_1("Function Sheet Subreport 1"),
    FUNCTION_SHEET_SUBREPORT_2("Function Sheet Subreport 2"),
    INTERNAL_GOOD_RECEIVED_NOTE("Internal Good Received Note"),
    ALACARTE_KOT_REPORT("Alacarte KOT Report"),
    
    
    
    ROOM_KOT_REPORT("Room KOT Report"),
    MAIN_BILL_ALACART_BOT_REPORT("Main Bill Alacart BOT Report"),
    MAIN_BILL_ALACART_KOT_REPORT("Main Bill Alacart KOT Report"),
    MAIN_BILL_BANQUET_BOT_REPORT("Main Bill Banquet BOT Report"),
    MAIN_BILL_BANQUET_KOT_REPORT("Main Bill Banquet KOT Report"),
    MAIN_BILL_ROOM_BOT_REPORT("Main Bill Room BOT Report"),
    MAIN_BILL_ROOM_KOT_REPORT("Main Bill Room KOT Report"),
    NATIONALITY("Nationality"),
    NATIONALITY_BY_COUNTRY("Nationality by country"),
    NO_SHOW("No Show"),
    PURCHASE_ORDER_SHEET("Purchase Order Sheet"),
    REGISTRATION_CARD("Registration Card"),
    RESEVATION_CARD("Reservation Card"),
    ROOM_MASTER_BILL_MAIN_REPORT("Room Master Bill Main Report"),
    ROOM_SALSE_BY_ROOM_NO_REPORT("Room Sales By Room No Report"),
    STORES_ISSUE_NOTE("Stores Issue Note"),
    STORES_REQUISITION_REPORT("Stores Requisition Report"),
    SUNDRY_BILL("Sundry Bill"),
    USER_REPORT("User Report");
 
   
     private final String val;
     
     private ReportType(String val){
     
     this.val = val;
     }
  
     @Override
    public String toString() {
        return val.toString();
    }
    
}
