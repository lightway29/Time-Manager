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
public class LaundryIDPopup {

    public SimpleStringProperty colLaundryId = new SimpleStringProperty("tcLaundryId");
    public SimpleStringProperty colDate = new SimpleStringProperty("tcDate");
    public SimpleStringProperty colRoomNo = new SimpleStringProperty("tcRoomNo");

    public String getColLaundryId() {
        return colLaundryId.get();
    }
    
    public String getColDate() {
        return colDate.get();
    }
    
    public String getColRoomNo() {
        return colRoomNo.get();
    }

    public TableView tableViewLoader(ObservableList observableList) {
        TableView tableView = new TableView();

        TableColumn tcLaundryId = new TableColumn("Laundry Id");
        tcLaundryId.setMinWidth(100);
        tcLaundryId.setCellValueFactory(
                new PropertyValueFactory<>("colLaundryId"));
        
        TableColumn tcDate = new TableColumn("Date");
        tcDate.setMinWidth(100);
        tcDate.setCellValueFactory(
                new PropertyValueFactory<>("colDate"));
        
        TableColumn tcRoomNo = new TableColumn("RoomNo");
        tcRoomNo.setMinWidth(100);
        tcRoomNo.setCellValueFactory(
                new PropertyValueFactory<>("colRoomNo"));

        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcLaundryId, tcDate, tcRoomNo);

        return tableView;
    }

}
