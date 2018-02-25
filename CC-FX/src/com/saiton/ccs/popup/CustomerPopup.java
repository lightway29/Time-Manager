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
public class CustomerPopup {

 
    public SimpleStringProperty colCustomerId = new SimpleStringProperty("tcCustomerId");
    public SimpleStringProperty colTitle = new SimpleStringProperty("tcTitle");
    public SimpleStringProperty colName = new SimpleStringProperty("tcName");
    //public SimpleStringProperty colIdType = new SimpleStringProperty("tcPassport");
    public SimpleStringProperty colAddress = new SimpleStringProperty("tcAddress");

    public String getColCustomerId() {
        return colCustomerId.get();
    }

    public String getColTitle() {
        return colTitle.get();
    }

    public String getColName() {
        return colName.get();
    }

//    public String getColIdType() {
//        return colIdType.get();
//    }

    public String getColAddress() {
        return colAddress.get();
    }

   public TableView tableViewLoader(ObservableList observableList){
       TableView tableView = new TableView();
       
       
       TableColumn tcCustomerId = new TableColumn("Customer Id");
        tcCustomerId.setMinWidth(100);
        tcCustomerId.setCellValueFactory(
                new PropertyValueFactory<>("colCustomerId"));

        TableColumn tcTitle = new TableColumn("Title");
        tcTitle.setMinWidth(100);
        tcTitle.setCellValueFactory(
                new PropertyValueFactory<>("colTitle"));

        TableColumn tcName = new TableColumn("Name");
        tcName.setMinWidth(100);
        tcName.setCellValueFactory(
                new PropertyValueFactory<>("colName"));

//        TableColumn tcPassport = new TableColumn("Id type");
//        tcPassport.setMinWidth(100);
//        tcPassport.setCellValueFactory(
//                new PropertyValueFactory<>("colIdType"));

        TableColumn tcAddress = new TableColumn("Address");
        tcAddress.setMinWidth(100);
        tcAddress.setCellValueFactory(
                new PropertyValueFactory<>("colAddress"));
        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcCustomerId, tcTitle, tcName ,tcAddress);
   
   
   
   
   return tableView;
   }

   
   
}
