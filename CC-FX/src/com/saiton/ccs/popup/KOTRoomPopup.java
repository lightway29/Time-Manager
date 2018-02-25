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
public class KOTRoomPopup {

 
    public SimpleStringProperty colRoomNo = new SimpleStringProperty("tcRoomNo");
    public SimpleStringProperty colCustomerID = new SimpleStringProperty("tcCustomerId");
    public SimpleStringProperty colCustomerName = new SimpleStringProperty("tcCustomerName");
    public SimpleStringProperty colSummaryID = new SimpleStringProperty("tcSummaryID");
    public SimpleStringProperty colPaxQty = new SimpleStringProperty("tcPaxQty");
    


    public String getColRoomNo() {
        return colRoomNo.get();
    }

    public String getColCustomerName() {
        return colCustomerName.get();
    }

    public String getColCustomerID() {
        return colCustomerID.get();
    }
    
    public String getColSummaryID() {
        return colSummaryID.get();
    }
    
    public String getColPaxQty() {
        return colPaxQty.get();
    }

   public TableView tableViewLoader(ObservableList observableList){
       TableView tableView = new TableView();
       
       
       TableColumn tcRoomNo = new TableColumn("Room No");
        tcRoomNo.setMinWidth(100);
        tcRoomNo.setCellValueFactory(
                new PropertyValueFactory<>("colRoomNo"));

        TableColumn tcCustomerName = new TableColumn("Customer Name");
        tcCustomerName.setMinWidth(100);
        tcCustomerName.setCellValueFactory(
                new PropertyValueFactory<>("colCustomerName"));

        TableColumn tcCustomerID = new TableColumn("Customer ID");
        tcCustomerID.setMinWidth(100);
        tcCustomerID.setCellValueFactory(
                new PropertyValueFactory<>("colCustomerID"));
        
        TableColumn tcSummaryID = new TableColumn("Summary ID");
        tcSummaryID.setMinWidth(100);
        tcSummaryID.setCellValueFactory(
                new PropertyValueFactory<>("colSummaryID"));
        
        TableColumn tcPaxQty = new TableColumn("PaxQty");
        tcPaxQty.setMinWidth(100);
        tcPaxQty.setCellValueFactory(
                new PropertyValueFactory<>("colPaxQty"));
        
        tableView.setItems(observableList);   
        tableView.getColumns().addAll(tcRoomNo, tcCustomerID, tcCustomerName,tcSummaryID, tcPaxQty);
   
   
   
   
   return tableView;
   }

   
   
}
