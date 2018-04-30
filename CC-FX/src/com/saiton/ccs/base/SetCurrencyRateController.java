/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.base;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author lightway
 */
public class SetCurrencyRateController implements Initializable {
    @FXML
    private Insets x2;
    @FXML
    private Label lblItemId1;
    @FXML
    private ComboBox<?> cmbName;
    @FXML
    private Button btnAddName;
    @FXML
    private Button btnRemoveName;
    @FXML
    private Label lblItemId12;
    @FXML
    private ComboBox<?> cmbClass;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnClose;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnAddNameOnAction(ActionEvent event) {
    }

    @FXML
    private void btnRemoveNameOnAction(ActionEvent event) {
    }

    @FXML
    private void btnSaveOnAction(ActionEvent event) {
    }

    @FXML
    private void btnCloseOnAction(ActionEvent event) {
    }
    
}
