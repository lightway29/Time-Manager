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
public class DescriptionPopup {

 
    public SimpleStringProperty colItemId = new SimpleStringProperty("tcItemId");
    public SimpleStringProperty colDescription = new SimpleStringProperty("tcDescription");
    public SimpleStringProperty colPrice = new SimpleStringProperty("tcPrice");
    public SimpleStringProperty colBatchNo = new SimpleStringProperty("tcBatchNo");

    public String getColItemId() {
        return colItemId.get();
    }

    public String getColDescription() {
        return colDescription.get();
    }

    public String getColPrice() {
        return colPrice.get();
    }

    public String getColBatchNo() {
        return colBatchNo.get();
    }

   public TableView tableViewLoader(ObservableList observableList){
       TableView tableView = new TableView();
       
       
       TableColumn tcItemId = new TableColumn("Item Id");
        tcItemId.setMinWidth(100);
        tcItemId.setCellValueFactory(
                new PropertyValueFactory<>("colItemId"));

        TableColumn tcDescription = new TableColumn("Description");
        tcDescription.setMinWidth(100);
        tcDescription.setCellValueFactory(
                new PropertyValueFactory<>("colDescription"));

        TableColumn tcPrice = new TableColumn("Price");
        tcPrice.setMinWidth(100);
        tcPrice.setCellValueFactory(
                new PropertyValueFactory<>("colPrice"));

        TableColumn tcBatchNo = new TableColumn("Batch No");
        tcBatchNo.setMinWidth(100);
        tcBatchNo.setCellValueFactory(
                new PropertyValueFactory<>("colBatchNo"));

        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcItemId, tcDescription, tcPrice, tcBatchNo);
   
   
   
   
   return tableView;
   }

   
   
}
