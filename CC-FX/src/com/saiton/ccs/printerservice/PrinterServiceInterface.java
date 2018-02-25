/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.printerservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;


public interface PrinterServiceInterface extends Remote {
    
     public boolean addNewPrintJob(String PrintTaskId,Map<String, Object> params) throws RemoteException;
    
}
