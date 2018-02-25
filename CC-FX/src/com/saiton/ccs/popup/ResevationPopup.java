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
public class ResevationPopup {

 
    public SimpleStringProperty colSummeryNo = new SimpleStringProperty("tcSummeryNo");
    public SimpleStringProperty colCustomerName = new SimpleStringProperty("tcCustomerName");
    public SimpleStringProperty colRoomNo = new SimpleStringProperty("tcRoomNo");
    public SimpleStringProperty colArrivalDate = new SimpleStringProperty("tcArrivalDate");
    public SimpleStringProperty colDepatureDate = new SimpleStringProperty("tcDepatureDate");

    public String getColSummeryNo() {
        return colSummeryNo.get();
    }

    public String getColCustomerName() {
        return colCustomerName.get();
    }

    public String getColRoomNo() {
        return colRoomNo.get();
    }

    public String getColArrivalDate() {
        return colArrivalDate.get();
    }
    
    public String getColDepatureDate() {
        return colDepatureDate.get();
    }
    

    

   public TableView tableViewLoader(ObservableList observableList){
       TableView tableView = new TableView();
       
 
    
       TableColumn tcSummeryNo = new TableColumn("Summery No");
        tcSummeryNo.setMinWidth(100);
        tcSummeryNo.setCellValueFactory(
                new PropertyValueFactory<>("colSummeryNo"));

        TableColumn tcCustomerName = new TableColumn("Customer Name");
        tcCustomerName.setMinWidth(100);
        tcCustomerName.setCellValueFactory(
                new PropertyValueFactory<>("colCustomerName"));

        TableColumn tcRoomNo = new TableColumn("Room No");
        tcRoomNo.setMinWidth(100);
        tcRoomNo.setCellValueFactory(
                new PropertyValueFactory<>("colRoomNo"));

        TableColumn tcArrivalDate = new TableColumn("Arrival Date");
        tcArrivalDate.setMinWidth(100);
        tcArrivalDate.setCellValueFactory(
                new PropertyValueFactory<>("colArrivalDate"));
        
        TableColumn tcDepatureDate = new TableColumn("Depature Date");
        tcDepatureDate.setMinWidth(100);
        tcDepatureDate.setCellValueFactory(
                new PropertyValueFactory<>("colDepatureDate"));

      
        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcSummeryNo, tcCustomerName, tcRoomNo, tcArrivalDate, tcDepatureDate);
   
   
   
   
   return tableView;
   }

   
   
}

