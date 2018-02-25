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
public class ItemPopup {

    public SimpleStringProperty colItemID = new SimpleStringProperty("tcItemID");
    public SimpleStringProperty colItemName = new SimpleStringProperty("tcItemName");
    public SimpleStringProperty colUnitPrice = new SimpleStringProperty("tcUnitPrice");
    public SimpleStringProperty colServiceCharges = new SimpleStringProperty("tcServiceCharges");
    public SimpleStringProperty colAvalibleQty = new SimpleStringProperty("tcAvalibleQty");

    public String getColItemID() {
        return colItemID.get();
    }
    
    public String getColItemName() {
        return colItemName.get();
    }
    
    public String getColUnitPrice() {
        return colUnitPrice.get();
    }
    
    public String getColServiceCharges() {
        return colServiceCharges.get();
    }
    
    public String getColAvalibleQty() {
        return colAvalibleQty.get();
    }


    public TableView tableViewLoader(ObservableList observableList) {
        TableView tableView = new TableView();

        TableColumn tcItemID = new TableColumn("Item ID");
        tcItemID .setMinWidth(100);
        tcItemID .setCellValueFactory(
                new PropertyValueFactory<>("colItemID"));
        
        TableColumn tcItemName = new TableColumn("Item Name");
        tcItemName.setMinWidth(100);
        tcItemName.setCellValueFactory(
                new PropertyValueFactory<>("colItemName"));
        
        TableColumn tcUnitPrice = new TableColumn("Unit Price");
        tcUnitPrice.setMinWidth(100);
        tcUnitPrice.setCellValueFactory(
                new PropertyValueFactory<>("colUnitPrice"));
        
        TableColumn tcServiceCharges = new TableColumn("Service Charges");
        tcServiceCharges.setMinWidth(100);
        tcServiceCharges.setCellValueFactory(
                new PropertyValueFactory<>("colServiceCharges"));
        
        TableColumn tcAvalibleQty = new TableColumn("Avalible Qty");
        tcAvalibleQty.setMinWidth(100);
        tcAvalibleQty.setCellValueFactory(
                new PropertyValueFactory<>("colAvalibleQty"));

        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcItemID , tcItemName , tcUnitPrice , tcServiceCharges, tcAvalibleQty);

        return tableView;
    }

}
