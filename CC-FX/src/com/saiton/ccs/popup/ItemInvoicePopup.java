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
public class ItemInvoicePopup {

    public SimpleStringProperty colItemID = new SimpleStringProperty("tcItemID");
    public SimpleStringProperty colBatchNo = new SimpleStringProperty("tcBatchNo");
    public SimpleStringProperty colItemName = new SimpleStringProperty("tcItemName");
    public SimpleStringProperty colUnit = new SimpleStringProperty("tcUnit");
//    public SimpleStringProperty colDescription = new SimpleStringProperty("tcDescription");

    public String getColItemID() {
        return colItemID.get();
    }
    
    public String getColBatchNo() {
        return colBatchNo.get();
    }

//    public String getColDescription() {
//        return colDescription.get();
//    }
    
    public String getColItemName() {
        return colItemName.get();
    }
    
    public String getColUnit() {
        return colUnit.get();
    }
    
    

    public TableView tableViewLoader(ObservableList observableList) {
        TableView tableView = new TableView();

        TableColumn tcItemID = new TableColumn("Item ID");
        tcItemID .setMinWidth(100);
        tcItemID .setCellValueFactory(
                new PropertyValueFactory<>("colItemID"));
        
        TableColumn tcBatchNo = new TableColumn("Description");
        tcBatchNo.setMinWidth(100);
        tcBatchNo.setCellValueFactory(
                new PropertyValueFactory<>("colBatchNo"));
        
        TableColumn tcItemName = new TableColumn("Item Name");
        tcItemName.setMinWidth(100);
        tcItemName.setCellValueFactory(
                new PropertyValueFactory<>("colItemName"));
        
        TableColumn tcUnit = new TableColumn("Part No");
        tcUnit.setMinWidth(100);
        tcUnit.setCellValueFactory(
                new PropertyValueFactory<>("colUnit"));
        
//        TableColumn tcDescription = new TableColumn("Description");
//        tcDescription.setMinWidth(100);
//        tcDescription.setCellValueFactory(
//                new PropertyValueFactory<>("colDescription"));

        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcItemID , tcBatchNo , tcItemName , tcUnit);

        return tableView;
    }

}
