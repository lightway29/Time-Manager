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
public class SundryRoomPopup {

 
    public SimpleStringProperty colRoomNo = new SimpleStringProperty("tcRoomNo");
    public SimpleStringProperty colCustomerName = new SimpleStringProperty("tcCustomerName");
    public SimpleStringProperty colSummaryId = new SimpleStringProperty("tcSummaryId");


    public String getColRoomNo() {
        return colRoomNo.get();
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

        TableColumn tcCustomerName = new TableColumn("Customer Name");
        tcCustomerName.setMinWidth(100);
        tcCustomerName.setCellValueFactory(
                new PropertyValueFactory<>("colCustomerName"));

        TableColumn tcSummaryId = new TableColumn("Summary Id");
        tcSummaryId.setMinWidth(100);
        tcSummaryId.setCellValueFactory(
                new PropertyValueFactory<>("colSummaryId"));
        
        tableView.setItems(observableList);   
        tableView.getColumns().addAll(tcRoomNo, tcCustomerName, tcSummaryId);
   
   
   
   
   return tableView;
   }

   
   
}
