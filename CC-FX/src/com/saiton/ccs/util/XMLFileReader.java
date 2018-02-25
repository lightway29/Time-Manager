/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.util;

import com.saiton.ccs.msgbox.MessageBox;
import com.saiton.ccs.printerservice.PrinterServiceServer;
import com.saiton.ccs.validations.ErrorMessages;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author lightway
 */
public class XMLFileReader {
    
    
    private boolean isServer = false;
    private String ip = null; 
    private String port = null;
    
    
    public XMLFileReader(){
    
        //Server configuration
        File file = new File("serverConfig.xml");

        if (file.exists()) {
     

            try {
                DocumentBuilderFactory docBuilderFactory
                        = DocumentBuilderFactory.
                        newInstance();
                DocumentBuilder docBuilder = docBuilderFactory.
                        newDocumentBuilder();
                Document doc = docBuilder.parse((file));

                NodeList serverConfigNode = doc.
                        getElementsByTagName("serverConfig");
                
                
                org.w3c.dom.Node serverNodeFirstElement = serverConfigNode.item(0);
                if (serverNodeFirstElement.getNodeType()
                        == org.w3c.dom.Node.ELEMENT_NODE) {

                    //-------Server Element----------
                    
                    Element serverElement = (Element) serverNodeFirstElement;

                    
                    org.w3c.dom.NodeList serverNode = serverElement.
                            getElementsByTagName("server");
                    Element serverNodeElement = (Element) serverNode.item(0);

                    org.w3c.dom.NodeList serverNodeItem = serverNodeElement.
                            getChildNodes();
                    
                    
                 

                    if (((org.w3c.dom.Node) serverNodeItem.item(0)).getNodeValue().
                            equals("1")) {
                        
                        isServer = true;
                        

//                        PrinterServiceServer printerServiceServer
//                                = new PrinterServiceServer();
//                        printerServiceServer.startPrintJobScanner();

                    }else{
                    
                    isServer = false;
                        
                    
                    }
                    //-------ip Element----------
                    
                    
                    org.w3c.dom.NodeList ipNode = serverElement.
                            getElementsByTagName("ip");
                    Element ipNodeElement = (Element) ipNode.item(0);

                    org.w3c.dom.NodeList ipNodeItem = ipNodeElement.
                            getChildNodes();
                    
                    if (((org.w3c.dom.Node) ipNodeItem.item(0)).getNodeValue()!= null) {
                        ip = ((org.w3c.dom.Node) ipNodeItem.item(0)).
                            getNodeValue();
                        
                    }
                    
                      org.w3c.dom.NodeList portNode = serverElement.
                            getElementsByTagName("port");
                    Element portNodeElement = (Element) portNode.item(0);

                    org.w3c.dom.NodeList portNodeItem = portNodeElement.
                            getChildNodes();
                   
                    if (((org.w3c.dom.Node) portNodeItem.item(0)).getNodeValue()!= null) {
                        port = ((org.w3c.dom.Node) portNodeItem.item(0)).
                            getNodeValue();
                        
                    }
                    
                    
                    
                }

//-------
                
                
                                 //-------ip Element----------
                    
                    
                  
                

//-------
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    
    
    
    
    
    
    
    }

    public boolean isIsServer() {
        return isServer;
    }

    public void setIsServer(boolean isServer) {
        this.isServer = isServer;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
    
}
