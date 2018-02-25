package com.saiton.ccs.printerservice;

//import com.lowagie.text.Document;
import com.saiton.ccs.msgbox.MessageBox;
import com.saiton.ccs.msgbox.SimpleMessageBoxFactory;
import com.saiton.ccs.util.XMLFileReader;
import com.saiton.ccs.validations.ErrorMessages;
import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;


import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.*;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
//import sun.print.IPPPrintService;





public class PrinterServiceClient {
    
    private XMLFileReader xmlFileReader = new XMLFileReader();
    
    private final MessageBox mb = SimpleMessageBoxFactory.createMessageBox();


    private String ipAddress = null;
    private int port;

    public PrinterServiceClient() {

        //Reading file thing here
        File file = new File("serverConfig.xml");

        if (file.exists()) {
        ipAddress = xmlFileReader.getIp();
        port = Integer.parseInt(xmlFileReader.getPort());
            System.out.println("Port In Client : "+port);
            System.out.println("ip In Client : "+ipAddress);
        }
        

        //-----------------------
    }

    public boolean createNewPrintJob(String printJobId,
            Map<String, Object> params) {
        boolean printStatus = false;
        try {

            Registry reg = LocateRegistry.getRegistry(ipAddress, port);
            PrinterServiceInterface printerServiceInterface
                    = (PrinterServiceInterface) reg.lookup("password");

            printStatus = printerServiceInterface.addNewPrintJob(
                    printJobId, params);

            System.out.println("Print Status : " + printStatus);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return printStatus;

    }

}
