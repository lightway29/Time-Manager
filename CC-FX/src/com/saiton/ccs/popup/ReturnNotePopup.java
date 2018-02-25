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
public class ReturnNotePopup {

    public SimpleStringProperty colReturnNoteId = new SimpleStringProperty("tcReturnNoteId");
    public SimpleStringProperty colDate = new SimpleStringProperty("tcDate");
//    public SimpleStringProperty colType = new SimpleStringProperty("tcType");

    public String getColReturnNoteId() {
        return colReturnNoteId.get();
    }
    
    public String getColDate() {
        return colDate.get();
    }
    
//    public String getColType() {
//        return colType.get();
//    }

    public TableView tableViewLoader(ObservableList observableList) {
        TableView tableView = new TableView();

        TableColumn tcReturnNoteId = new TableColumn("Return Note Id");
        tcReturnNoteId.setMinWidth(100);
        tcReturnNoteId.setCellValueFactory(
                new PropertyValueFactory<>("colReturnNoteId"));
        
        TableColumn tcDate = new TableColumn("Date");
        tcDate.setMinWidth(100);
        tcDate.setCellValueFactory(
                new PropertyValueFactory<>("colDate"));
        
//        TableColumn tcType = new TableColumn("Type");
//        tcType.setMinWidth(100);
//        tcType.setCellValueFactory(
//                new PropertyValueFactory<>("colType"));

        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcReturnNoteId, tcDate);

        return tableView;
    }

}
