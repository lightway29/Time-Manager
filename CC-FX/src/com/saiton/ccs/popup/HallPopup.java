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
public class HallPopup {

 
    public SimpleStringProperty colHallId = new SimpleStringProperty("tcHallId");
    public SimpleStringProperty colTitle = new SimpleStringProperty("tcTitle");


    public String getColHallId() {
        return colHallId.get();
    }

    public String getColTitle() {
        return colTitle.get();
    }

   

   public TableView tableViewLoader(ObservableList observableList){
       TableView tableView = new TableView();
       
       
       TableColumn tcHallId = new TableColumn("Hall Id");
        tcHallId.setMinWidth(100);
        tcHallId.setCellValueFactory(
                new PropertyValueFactory<>("colHallId"));

        TableColumn tcTitle = new TableColumn("Title");
        tcTitle.setMinWidth(100);
        tcTitle.setCellValueFactory(
                new PropertyValueFactory<>("colTitle"));

      
        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcHallId, tcTitle);
   
   
   
   
   return tableView;
   }

   
   
}
