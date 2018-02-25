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
public class PurchaseOrderPopup {

 
    public SimpleStringProperty colPurchaseOrderId = new SimpleStringProperty("tcPurcahseOrderId");
    public SimpleStringProperty colDate = new SimpleStringProperty("tcDate");
    public SimpleStringProperty colSupplierId = new SimpleStringProperty("tcSupplierId");
    public SimpleStringProperty colSupplierName = new SimpleStringProperty("tcSupplierName");

    public String getColPurchaseOrderId() {
        return colPurchaseOrderId.get();
    }
    
    public String getColDate() {
        return colDate.get();
    }
    
    public String getColSupplierId() {
        return colSupplierId.get();
    }
    
        public String getColSupplierName() {
        return colSupplierName.get();
    }


   public TableView tableViewLoader(ObservableList observableList){
       TableView tableView = new TableView();
       
       
       TableColumn tcPurcahseOrderId = new TableColumn("Purchase Order Id");
        tcPurcahseOrderId.setMinWidth(100);
        tcPurcahseOrderId.setCellValueFactory(
                new PropertyValueFactory<>("colPurchaseOrderId"));
        
        TableColumn tcDate = new TableColumn("Date");
        tcDate.setMinWidth(100);
        tcDate.setCellValueFactory(
                new PropertyValueFactory<>("colDate"));
        
        TableColumn tcSupplierId = new TableColumn("Supplier Id");
        tcSupplierId.setMinWidth(100);
        tcSupplierId.setCellValueFactory(
                new PropertyValueFactory<>("colSupplierId"));
        
        TableColumn tcSupplierName = new TableColumn("Supplier Name");
        tcSupplierName.setMinWidth(100);
        tcSupplierName.setCellValueFactory(
                new PropertyValueFactory<>("colSupplierName"));

        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcPurcahseOrderId, tcDate, tcSupplierId,tcSupplierName);
   
   return tableView;
   }

}
