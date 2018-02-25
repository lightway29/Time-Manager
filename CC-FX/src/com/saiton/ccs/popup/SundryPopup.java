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
public class SundryPopup {

    public SimpleStringProperty colSundryId = new SimpleStringProperty("tcSundryId");
    public SimpleStringProperty colDate = new SimpleStringProperty("tcDate");

    public String getColSundryId() {
        return colSundryId.get();
    }
    
    public String getColDate() {
        return colDate.get();
    }

    public TableView tableViewLoader(ObservableList observableList) {
        TableView tableView = new TableView();

        TableColumn tcSundryId = new TableColumn("Sundry Id");
        tcSundryId.setMinWidth(100);
        tcSundryId.setCellValueFactory(
                new PropertyValueFactory<>("colSundryId"));
        
        TableColumn tcDate = new TableColumn("Date");
        tcDate.setMinWidth(100);
        tcDate.setCellValueFactory(
                new PropertyValueFactory<>("colDate"));

        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcSundryId, tcDate);

        return tableView;
    }

}
