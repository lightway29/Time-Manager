/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.printerservice;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


class PrinterService extends UnicastRemoteObject{
    
    
     public PrinterService(String PrintTaskId)throws RemoteException{
    // addNewPrintJob(PrintTaskId);
     }
     
//    public boolean addNewPrintJob(String PrintTaskId) throws RemoteException {
//        boolean printStatus = true;
//        
//        System.out.println("Printing In progress...");
//        System.out.println("Id to use to select print data from db : "+PrintTaskId);
//        System.out.println("Printers Connected : "+PrinterReport.getPrinterServiceID());
//        
////        PrinterReport printReport = new PrinterReport(PrintTaskId, printerID,
////                null)
////        
//        
//        return printStatus;
//         
//    }
}
