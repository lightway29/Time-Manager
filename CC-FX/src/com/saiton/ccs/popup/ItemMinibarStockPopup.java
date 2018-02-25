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
public class ItemMinibarStockPopup {

    public SimpleStringProperty colItemID = new SimpleStringProperty("tcItemID");
    public SimpleStringProperty colItemName = new SimpleStringProperty("tcItemName");
    public SimpleStringProperty colBatchNo = new SimpleStringProperty("tcBatchNo");
    public SimpleStringProperty colUnit = new SimpleStringProperty("tcUnit");
    public SimpleStringProperty colQty = new SimpleStringProperty("tcQty");

    public String getColItemID() {
        return colItemID.get();
    }
    
    public String getColItemName() {
        return colItemName.get();
    }
    
    public String getColBatchNo() {
        return colBatchNo.get();
    }
    
    public String getColUnit() {
        return colUnit.get();
    }
    
    public String getColQty() {
        return colQty.get();
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
        
        TableColumn tcBatchNo = new TableColumn("Batch No");
        tcBatchNo.setMinWidth(100);
        tcBatchNo.setCellValueFactory(
                new PropertyValueFactory<>("colBatchNo"));
        
        TableColumn tcUnit = new TableColumn("Unit");
        tcUnit.setMinWidth(100);
        tcUnit.setCellValueFactory(
                new PropertyValueFactory<>("colUnit"));
        
        TableColumn tcQty = new TableColumn("Qty");
        tcQty.setMinWidth(100);
        tcQty.setCellValueFactory(
                new PropertyValueFactory<>("colQty"));

        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcItemID , tcItemName , tcBatchNo , tcUnit , tcQty);

        return tableView;
    }

}
