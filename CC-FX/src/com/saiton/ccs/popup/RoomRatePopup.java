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
public class RoomRatePopup {

 
    public SimpleStringProperty colRateCode = new SimpleStringProperty("tcRateCode");
    public SimpleStringProperty colSeason = new SimpleStringProperty("tcSeason");
    public SimpleStringProperty colRoomBasis = new SimpleStringProperty("tcRoomBasis");

    public String getColRateCode() {
        return colRateCode.get();
    }
    
    public String getColSeason() {
        return colSeason.get();
    }
    
    public String getColRoomBasis() {
        return colRoomBasis.get();
    }
    
   public TableView tableViewLoader(ObservableList observableList){
       TableView tableView = new TableView();
       
       
       TableColumn tcRateCode = new TableColumn("Rate Code");
        tcRateCode.setMinWidth(100);
        tcRateCode.setCellValueFactory(
                new PropertyValueFactory<>("colRateCode"));
        
        TableColumn tcSeason = new TableColumn("Season");
        tcSeason.setMinWidth(100);
        tcSeason.setCellValueFactory(
                new PropertyValueFactory<>("colSeason"));
        
        TableColumn tcRoomBasis = new TableColumn("RoomBasis");
        tcRoomBasis.setMinWidth(100);
        tcRoomBasis.setCellValueFactory(
                new PropertyValueFactory<>("colRoomBasis"));

        tableView.setItems(observableList);
        tableView.getColumns().addAll(tcRateCode,tcSeason,tcRoomBasis);
   
   return tableView;
   }

}
