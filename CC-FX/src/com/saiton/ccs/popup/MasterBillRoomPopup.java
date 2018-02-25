/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.popup;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Polypackaging-A1
 */
public class MasterBillRoomPopup {

 
    public SimpleStringProperty colRoomNo = new SimpleStringProperty("tcRoomNo");
    public SimpleStringProperty colCustomerID = new SimpleStringProperty("tcCustomerID");
    public SimpleStringProperty colCustomerName = new SimpleStringProperty("tcCustomerName");
    public SimpleStringProperty colSummaryId = new SimpleStringProperty("tcSummaryId");


    public String getColRoomNo() {
        return colRoomNo.get();
    }
    
    public String getColCustomerID() {
        return colCustomerID.get();
    }

    public String getColCustomerName() {
        return colCustomerName.get();
    }

    public String getColSummaryId() {
        return colSummaryId.get();
    }

   public TableView tableViewLoader(ObservableList observableList){
       TableView tableView = new TableView();
       
       
       TableColumn tcRoomNo = new TableColumn("Room No");
        tcRoomNo.setMinWidth(100);
        tcRoomNo.setCellValueFactory(
                new PropertyValueFactory<>("colRoomNo"));
        
        TableColumn tcCustomerID = new TableColumn("Customer ID");
        tcCustomerID.setMinWidth(100);
        tcCustomerID.setCellValueFactory(
                new PropertyValueFactory<>("colCustomerID"));

        TableColumn tcCustomerName = new TableColumn("Customer Name");
        tcCustomerName.setMinWidth(100);
        tcCustomerName.setCellValueFactory(
                new PropertyValueFactory<>("colCustomerName"));

        TableColumn tcSummaryId = new TableColumn("Summary Id");
        tcSummaryId.setMinWidth(100);
        tcSummaryId.setCellValueFactory(
                new PropertyValueFactory<>("colSummaryId"));
        
        tableView.setItems(observableList);   
        tableView.getColumns().addAll(tcRoomNo, tcCustomerID, tcCustomerName, tcSummaryId);
   
   
   
   
   return tableView;
   }

   
   
}
