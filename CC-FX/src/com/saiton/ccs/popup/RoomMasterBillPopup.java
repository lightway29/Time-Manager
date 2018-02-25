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
public class RoomMasterBillPopup {

 
    public SimpleStringProperty colMasterBillId = new SimpleStringProperty("tcMasterBillId");
    public SimpleStringProperty colDate = new SimpleStringProperty("tcDate");
    public SimpleStringProperty colCustomerId = new SimpleStringProperty("tcCustomerId");
    public SimpleStringProperty colCustomerName = new SimpleStringProperty("tcCustomerName");
    public SimpleStringProperty colSummaryId = new SimpleStringProperty("tcSummaryId");

    public String getColMasterBillId() {
        return colMasterBillId.get();
    }
    
    public String getColDate() {
        return colDate.get();
    }
    
    public String getColCustomerId() {
        return colCustomerId.get();
    }
    
    public String getColCustomerName() {
        return colCustomerName.get();
    }
    
    public String getColSummaryId() {
        return colSummaryId.get();
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
        
        TableColumn tcCustomerId = new TableColumn("Customer ID");
        tcCustomerId.setMinWidth(100);
        tcCustomerId.setCellValueFactory(
                new PropertyValueFactory<>("colCustomerId"));
        
        TableColumn tcCustomerName = new TableColumn("Customer Name");
        tcCustomerName.setMinWidth(100);
        tcCustomerName.setCellValueFactory(
                new PropertyValueFactory<>("colCustomerName"));
        
        TableColumn tcSummaryId = new TableColumn("Summary ID");
        tcSummaryId.setMinWidth(100);
        tcSummaryId.setCellValueFactory(
                new PropertyValueFactory<>("colSummaryId"));

        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcMasterBillId, tcDate, tcCustomerId, tcCustomerName,tcSummaryId);
   
   return tableView;
   }

}
