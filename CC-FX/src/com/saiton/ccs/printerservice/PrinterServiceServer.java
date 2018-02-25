/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.printerservice;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Polypackaging-A1
 */
public class PrinterServiceServer {

    int portNo = 1099;

    public PrinterServiceServer() {
        
        //Reading file for port no here
    }

    public void startPrintJobScanner() {

        System.out.println("Print Job Scanner is activated in server.");
        System.out.println("Scanning For Print Job ...");

        try {
            
            Registry reg = LocateRegistry.createRegistry(portNo);
             PrinterServiceImplementation service = new PrinterServiceImplementation();

            reg.rebind("password", service);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
