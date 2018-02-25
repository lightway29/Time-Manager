/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.settings;

import com.saiton.ccs.msgbox.MessageBox;
import com.saiton.ccs.msgbox.SimpleMessageBoxFactory;
import com.saiton.ccs.settingsdao.InvoiceSettingsDAO;
import com.saiton.ccs.stockdao.ItemDAO;
import com.saiton.ccs.uihandle.StagePassable;
import com.saiton.ccs.validations.CustomTextFieldValidationImpl;
import com.saiton.ccs.validations.ErrorMessages;
import com.saiton.ccs.validations.FormatAndValidate;
import com.saiton.ccs.validations.Validatable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;

/**
 * FXML Controller class
 *
 * @author Isu
 */
public class InvoiceSettingsController implements Initializable, Validatable,
        StagePassable {

    @FXML
    private Label lblInvoiceNo;
    @FXML
    private RadioButton radioTax;
    @FXML
    private ToggleGroup tax;
    @FXML
    private RadioButton radioNonTax;
    @FXML
    private TextField txtInvoiceNo;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    int taxStatus = 0;

    private final ValidationSupport validationSupport = new ValidationSupport();
    private final FormatAndValidate fav = FormatAndValidate.getInstance();
    private Stage stage;
    InvoiceSettingsDAO invDAO = new InvoiceSettingsDAO();
    private MessageBox mb;
    String inv = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mb = SimpleMessageBoxFactory.createMessageBox();
        
        loadId();
    }

    @FXML
    private void radioTaxOnAction(ActionEvent event) {

        loadId();
    }

    @FXML
    private void radioNonTaxOnAction(ActionEvent event) {
        loadId();
    }

    @FXML
    private void txtItemDiscountOnKeyReleased(KeyEvent event) {
        validatorInitialization();
    }

    @FXML
    private void buttonSaveOnActionEvent(ActionEvent event) {
        if (txtInvoiceNo.getText().length() > 0) {
            inv = txtInvoiceNo.getText().substring(0, 3);

            validatorInitialization();
            boolean status = false;

            if (radioTax.isSelected()) {
                taxStatus = 1;
                status = invDAO.checkingTaxAvailability(taxStatus, txtInvoiceNo.getText());
            } else if (radioNonTax.isSelected()) {
                taxStatus = 0;
                status = invDAO.checkingTaxAvailability(taxStatus, txtInvoiceNo.getText());
            } else {
                status = false;
            }

            if (status == true) {
                mb.ShowMessage(stage,
                        ErrorMessages.SuccesfullyCreated, "Information",
                        MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                        MessageBox.MessageType.MSG_OK);

                clearInput();

            } else {
                mb.ShowMessage(stage,
                        ErrorMessages.NotSuccesfullyCreated, "Error",
                        MessageBox.MessageIcon.MSG_ICON_FAIL,
                        MessageBox.MessageType.MSG_OK);
            }
        } else {
            mb.ShowMessage(stage,
                    ErrorMessages.Empty, "Error",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            
            loadId();

        }
    }

    @FXML
    private void btnCancelOnAction(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void clearInput() {
        txtInvoiceNo.setText("");
        radioTax.setSelected(true);
    }

    @Override
    public void clearValidations() {

    }

    @Override
    public void setStage(Stage stage, Object[] obj) {
        this.stage = stage;
        stage.setHeight(200.0);
        stage.setWidth(400.0);

        validatorInitialization();
    }

    private void validatorInitialization() {

        System.gc();

        //Invoice No
        validationSupport.registerValidator(txtInvoiceNo,
                new CustomTextFieldValidationImpl(txtInvoiceNo,
                        !fav.validName(txtInvoiceNo.getText()),
                        ErrorMessages.InvalidId));
    }

    private void loadId() {

        if (radioTax.isSelected()) {
            if (invDAO.loadId("1") != null) {
                txtInvoiceNo.setText(invDAO.loadId("1"));
            } else {
                txtInvoiceNo.setText("INT0001");
            }
        } else if (radioNonTax.isSelected()) {
            if (invDAO.loadId("0") != null) {
                txtInvoiceNo.setText(invDAO.loadId("0"));
            } else {
                txtInvoiceNo.setText("INV0001");
            }
        }
    }
}
