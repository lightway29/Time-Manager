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
public class ServiceInfoPopup {

    public SimpleStringProperty colItemID = new SimpleStringProperty("tcItemID");
    public SimpleStringProperty colItemName = new SimpleStringProperty("tcItemName");
    public SimpleStringProperty colItemDesc = new SimpleStringProperty("tcItemDesc");
    public SimpleStringProperty colItemPrice = new SimpleStringProperty("tcItemPrice");
    
    public String getColItemID() {
        return colItemID.get();
    }
    
    public String getColItemName() {
        return colItemName.get();
    }
    
     public String getColItemDesc() {
        return colItemDesc.get();
    }
    
    public String getColItemPrice() {
        return colItemPrice.get();
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
        
         TableColumn tcItemDesc = new TableColumn("Description");
        tcItemDesc.setMinWidth(100);
        tcItemDesc.setCellValueFactory(
                new PropertyValueFactory<>("colItemDesc"));
        
         TableColumn tcItemPrice = new TableColumn("Price");
        tcItemPrice.setMinWidth(100);
        tcItemPrice.setCellValueFactory(
                new PropertyValueFactory<>("colItemPrice"));
        

        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcItemID ,
                tcItemName ,
                tcItemDesc ,
                tcItemPrice);

        return tableView;
    }

}
