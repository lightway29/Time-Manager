/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.popup;

import com.saiton.ccs.base.CustomerRegistrationController;
import com.saiton.ccs.basedao.CustomerRegistrationDAO;
import com.saiton.ccs.uihandle.StagePassable;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author Polypackaging-A1
 */
public class AlacarteMasterBillPopup {

 
    public SimpleStringProperty colMasterBillId = new SimpleStringProperty("tcMasterBillId");
    public SimpleStringProperty colDate = new SimpleStringProperty("tcDate");
    public SimpleStringProperty colCustomerName = new SimpleStringProperty("tcCustomerName");
    public SimpleStringProperty colSessionId = new SimpleStringProperty("tcSessionId");

    public String getColMasterBillId() {
        return colMasterBillId.get();
    }
    
    public String getColDate() {
        return colDate.get();
    }
    
    public String getColCustomerName() {
        return colCustomerName.get();
    }
    
    public String getColSessionId() {
        return colSessionId.get();
    }



   public TableView tableViewLoader(ObservableList observableList){
       TableView tableView = new TableView();
       
       
       TableColumn tcMasterBillId = new TableColumn("Master Bill Id");
        tcMasterBillId.setMinWidth(100);
        tcMasterBillId.setCellValueFactory(
                new PropertyValueFactory<>("colMasterBillId"));
        
        TableColumn tcDate = new TableColumn("Date");
        tcDate.setMinWidth(100);
        tcDate.setCellValueFactory(
                new PropertyValueFactory<>("colDate"));
        
        TableColumn tcCustomerName = new TableColumn("Customer Name");
        tcCustomerName.setMinWidth(100);
        tcCustomerName.setCellValueFactory(
                new PropertyValueFactory<>("colCustomerName"));
        
        TableColumn tcSessionId = new TableColumn("Session ID");
        tcSessionId.setMinWidth(100);
        tcSessionId.setCellValueFactory(
                new PropertyValueFactory<>("colSessionId"));

        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcMasterBillId, tcDate, tcCustomerName,tcSessionId);
   
   return tableView;
   }

}
