
package com.saiton.ccs.printerservice;

import com.saiton.ccs.msgbox.MessageBox;
import com.saiton.ccs.printerservicedao.PrinterServiceDAO;
import com.saiton.ccs.validations.ErrorMessages;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;

public class PrinterServiceImplementation extends UnicastRemoteObject implements
        PrinterServiceInterface {

    private PrinterServiceDAO printerServiceDAO = new PrinterServiceDAO();
    private MessageBox mb;

    public PrinterServiceImplementation() throws RemoteException {
    }

    public boolean addNewPrintJob(String PrintTaskId, Map<String, Object> params)
            throws RemoteException {

        boolean printStatus = true;

        System.out.println("Printing In progress...");

        System.out.println("Printers Connected : " + PrinterReport.
                getPrinterServiceID());

        ArrayList<String> connectedPrinter = PrinterReport.
                getPrinterServiceID();

        ArrayList<String> list = printerServiceDAO.getPrintJobInfo(PrintTaskId);

        String url = printerServiceDAO.getReportURL(list.get(0));
        String printerName = printerServiceDAO.getPrintName(list.get(1));
        
            

        for (int i = 0; i < connectedPrinter.size(); i++) {
         System.out.println(
                            "-------------Issue Tracker-------------- : "+connectedPrinter.get(i)+" : "+printerName);
            if (connectedPrinter.get(i).equals(printerName)) {
   
                PrinterReport printerReport = new PrinterReport(url,i,
                        params);
                printStatus = printerReport.print();
               

            }

        }

        
        return printStatus;
    }
}
