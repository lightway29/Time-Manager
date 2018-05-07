/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.popup;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Lightway
 */
public class TimeTablePopup {

 
    public SimpleStringProperty colTimetableId = new SimpleStringProperty("tcTimetableId");
    public SimpleStringProperty colTitle = new SimpleStringProperty("tcTitle");
   

    public String getColTimetableId() {
        return colTimetableId.get();
    }

    public String getColTitle() {
        return colTitle.get();
    }



   public TableView tableViewLoader(ObservableList observableList){
       TableView tableView = new TableView();
       
       
       TableColumn tcTimetableId = new TableColumn("Timetable Id");
        tcTimetableId.setMinWidth(100);
        tcTimetableId.setCellValueFactory(
                new PropertyValueFactory<>("colTimetableId"));

        TableColumn tcTitle = new TableColumn("Title");
        tcTitle.setMinWidth(100);
        tcTitle.setCellValueFactory(
                new PropertyValueFactory<>("colTitle"));

        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcTimetableId, tcTitle);
   
   
   
   
   return tableView;
   }

   
   
}
