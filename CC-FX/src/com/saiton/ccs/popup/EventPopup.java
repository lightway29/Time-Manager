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
public class EventPopup {

 
    public SimpleStringProperty colCustomerId = new SimpleStringProperty("tcCustomerId");
    public SimpleStringProperty colEventId = new SimpleStringProperty("tcEventId");
    public SimpleStringProperty colEventName = new SimpleStringProperty("tcEventName");
    public SimpleStringProperty colEventDate = new SimpleStringProperty("tcEventDate");

    public String getColCustomerId() {
        return colCustomerId.get();
    }

    public String getColEventId() {
        return colEventId.get();
    }

    public String getColEventName() {
        return colEventName.get();
    }

    public String getColEventDate() {
        return colEventDate.get();
    }
    

    

   public TableView tableViewLoader(ObservableList observableList){
       TableView tableView = new TableView();
       
 
    
       TableColumn tcCustomerId = new TableColumn("Customer Id");
        tcCustomerId.setMinWidth(100);
        tcCustomerId.setCellValueFactory(
                new PropertyValueFactory<>("colCustomerId"));

        TableColumn tcEventId = new TableColumn("Event Id");
        tcEventId.setMinWidth(100);
        tcEventId.setCellValueFactory(
                new PropertyValueFactory<>("colEventId"));

        TableColumn tcEventName = new TableColumn("Event Name");
        tcEventName.setMinWidth(100);
        tcEventName.setCellValueFactory(
                new PropertyValueFactory<>("colEventName"));

        TableColumn tcEventDate = new TableColumn("Date");
        tcEventDate.setMinWidth(100);
        tcEventDate.setCellValueFactory(
                new PropertyValueFactory<>("colEventDate"));

      
        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcCustomerId, tcEventId, tcEventName, tcEventDate);
   
   
   
   
   return tableView;
   }

   
   
}

