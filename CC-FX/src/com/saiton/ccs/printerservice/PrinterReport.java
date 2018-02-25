/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.printerservice;

import static com.saiton.ccs.printerservicedao.PrinterServiceDAO.star;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.Map;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;

/**
 *
 * @author kasun
 */
public class PrinterReport {

    private String jasperPath = null;
    private int printerID = 0;
    private Map<String, Object> params = null;

    public PrinterReport(String jasperPath, int printerID, Map<String, Object> params) {
        this.jasperPath = jasperPath;
        this.printerID = printerID;
        this.params = params;
    }

    
    
    public boolean print() {
        try {
            JasperPrint print = JasperFillManager.fillReport(jasperPath, params,star.con);
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            PrintService[] service = PrintServiceLookup.lookupPrintServices(null, null);

            printerJob.setPrintService(service[printerID]);
            PrintRequestAttributeSet attrSet = new HashPrintRequestAttributeSet();
            MediaSizeName mediaSizeName = MediaSize.findMedia(4, 4, MediaSize.INCH);

            attrSet.add(mediaSizeName);
            attrSet.add(new Copies(1));

            JRPrintServiceExporter exporter = new JRPrintServiceExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, service[printerID]);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, service[printerID].getAttributes());
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, attrSet);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
            
            exporter.exportReport();
            return true;
        } catch (JRException | PrinterException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static ArrayList<String> getPrinterServiceID(){
        
        ArrayList<String> serialNo = new ArrayList<>();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
       
        for (PrintService service : services) {
            serialNo.add(service.getName());
        }
        return serialNo;
    }
}
