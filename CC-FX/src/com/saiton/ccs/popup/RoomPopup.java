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
public class RoomPopup {

 
    public SimpleStringProperty colRoomNo = new SimpleStringProperty("tcRoomNo");
    public SimpleStringProperty colFloor = new SimpleStringProperty("tcFloor");
    public SimpleStringProperty colLocation = new SimpleStringProperty("tcLocation");
    public SimpleStringProperty colType = new SimpleStringProperty("tcType");
    public SimpleStringProperty colStatus = new SimpleStringProperty("tcStatus");
     public SimpleStringProperty colMinibar = new SimpleStringProperty("tcMinibar");

    public String getColRoomNo() {
        return colRoomNo.get();
    }

    public String getColFloor() {
        return colFloor.get();
    }

    public String getColLocation() {
        return colLocation.get();
    }

    public String getColType() {
        return colType.get();
    }

    public String getColStatus() {
        return colStatus.get();
    }
    
    public String getColMinibar() {
        return colMinibar.get();
    }

   public TableView tableViewLoader(ObservableList observableList){
       TableView tableView = new TableView();
       
       
       TableColumn tcRoomNo = new TableColumn("Room No");
        tcRoomNo.setMinWidth(100);
        tcRoomNo.setCellValueFactory(
                new PropertyValueFactory<>("colRoomNo"));

        TableColumn tcFloor = new TableColumn("Floor");
        tcFloor.setMinWidth(100);
        tcFloor.setCellValueFactory(
                new PropertyValueFactory<>("colFloor"));

        TableColumn tcLocation = new TableColumn("Location");
        tcLocation.setMinWidth(100);
        tcLocation.setCellValueFactory(
                new PropertyValueFactory<>("colLocation"));

        TableColumn tcType = new TableColumn("Type");
        tcType.setMinWidth(100);
        tcType.setCellValueFactory(
                new PropertyValueFactory<>("colType"));

        TableColumn tcStatus = new TableColumn("Status");
        tcStatus.setMinWidth(100);
        tcStatus.setCellValueFactory(
                new PropertyValueFactory<>("colStatus"));
        
        TableColumn tcMinibar = new TableColumn("Minibar");
        tcMinibar.setMinWidth(100);
        tcMinibar.setCellValueFactory(
                new PropertyValueFactory<>("colMinibar"));
        
        tableView.setItems(observableList);   
        tableView.getColumns().addAll(tcRoomNo, tcFloor, tcLocation, tcType, tcStatus, tcMinibar);
   
   
   
   
   return tableView;
   }

   
   
}
