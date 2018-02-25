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
public class SessionPopup {

    public SimpleStringProperty colCustomerName = new SimpleStringProperty("tcCustomerName");
    public SimpleStringProperty colTable = new SimpleStringProperty("tcTable");
    public SimpleStringProperty colSessionID = new SimpleStringProperty("tcSessionID");
    public SimpleStringProperty colSelectionID = new SimpleStringProperty("tcSelectionID");

    public String getColCustomerName() {
        return colCustomerName.get();
    }
    
    public String getColTable() {
        return colTable.get();
    }
    
    public String getColSessionID() {
        return colSessionID.get();
    } 
    
    public String getColSelectionID() {
        return colSelectionID.get();
    } 
    

    public TableView tableViewLoader(ObservableList observableList) {
        TableView tableView = new TableView();

        TableColumn tcCustomerName = new TableColumn("Customer Name");
        tcCustomerName.setMinWidth(100);
        tcCustomerName.setCellValueFactory(
                new PropertyValueFactory<>("colCustomerName"));
        
        TableColumn tcTable = new TableColumn("Table");
        tcTable.setMinWidth(100);
        tcTable.setCellValueFactory(
                new PropertyValueFactory<>("colTable"));
        
        TableColumn tcSessionID = new TableColumn("Session ID");
        tcSessionID.setMinWidth(100);
        tcSessionID.setCellValueFactory(
                new PropertyValueFactory<>("colSessionID"));
        
        TableColumn tcTo = new TableColumn("To");
        tcTo.setMinWidth(100);
        tcTo.setCellValueFactory(
                new PropertyValueFactory<>("colTo"));
        
        TableColumn tcSelectionID = new TableColumn("Selection ID");
        tcSelectionID.setMinWidth(100);
        tcSelectionID.setCellValueFactory(
                new PropertyValueFactory<>("colSelectionID"));
        
        

        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcCustomerName, tcTable, tcSessionID,tcSelectionID);

        return tableView;
    }

}
