/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.base;

import com.saiton.ccs.basedao.SetCurrencyRateDAO;
import com.saiton.ccs.msgbox.MessageBox;
import com.saiton.ccs.msgbox.SimpleMessageBoxFactory;
import com.saiton.ccs.uihandle.StagePassable;
import com.saiton.ccs.validations.CustomTextFieldValidationImpl;
import com.saiton.ccs.validations.ErrorMessages;
import com.saiton.ccs.validations.FormatAndValidate;
import com.saiton.ccs.validations.Validatable;
import com.saiton.ccs.database.Starter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;

/**
 * FXML Controller class
 *
 * @author MewanZ
 */
public class SetCurrencyRateController extends AnchorPane implements Initializable, Validatable,StagePassable {

//<editor-fold defaultstate="collapsed" desc="initcomponents">
     @FXML
    private Button btnClose;

    @FXML
    private Label lblItemId121;

    @FXML
    private Button btnSave;

    @FXML
    private Label lblItemId1;

    @FXML
    private Label lblItemId11;

    @FXML
    private Label lblItemId12;

    @FXML
    private Insets x2;

    @FXML
    private TextField txtCurrentRateUSD;

    @FXML
    private TextField txtNewRateLKR;

    @FXML
    private TextField txtCurrentRateLKR;

    @FXML
    private TextField txtNewRateUSD;  
  
    
//</editor-fold>
    
    private Stage stage;
    ValidationSupport Validate = new ValidationSupport();
    private final FormatAndValidate fav = new FormatAndValidate();
    public static Starter star;
    
    SetCurrencyRateDAO Currency=new SetCurrencyRateDAO();
   
    
    public void clearAll(){
        txtNewRateLKR.setText("");
        txtNewRateUSD.setText("");
        txtCurrentRateUSD.setText(Currency.getValue("usd_rate"));
        txtCurrentRateLKR.setText(Currency.getValue("lkr_rate"));
    }
    
    private final MessageBox messageBox = SimpleMessageBoxFactory.createMessageBox();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtCurrentRateUSD.setText(Currency.getValue("usd_rate"));
        txtCurrentRateLKR.setText(Currency.getValue("lkr_rate"));
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void clearInput() {
        
    }

    @Override
    public void clearValidations() {
        
    }

    @Override
    public void setStage(Stage stage, Object[] obj) {
        this.stage = stage;
    }


    @FXML
    void btnSaveOnAction(ActionEvent event) {
        validate();
        if (!txtNewRateLKR.getText().isEmpty() && !txtNewRateUSD.getText().isEmpty() &&  messageBox.
                ShowMessage(stage,
                        "Are you sure you want to change Currency Rate ?"
                        ,
                        "Currency Rate Change!",
                        MessageBox.MessageIcon.MSG_ICON_NONE,
                        MessageBox.MessageType.MSG_YESNO)
                == MessageBox.MessageOutput.MSG_YES) {
            
            Currency.setValue(txtNewRateLKR.getText(),txtNewRateUSD.getText());
            clearAll();
        }
    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {
          stage.close();
    }

    @FXML
    void txtCurrentRateLKROnKeyReleased(KeyEvent event) {
        validate();
    }

    @FXML
    void txtCurrentRateUSDOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtNewRateUSDOnKeyReleased(KeyEvent event) {
        validate();
    }

    @FXML
    void txtNewRateLKROnKeyReleased(KeyEvent event) {
        validate();
        
    }
    
    private void validate() {
        Validate.registerValidator(txtNewRateUSD,
                new CustomTextFieldValidationImpl(txtNewRateUSD, !fav.
                        validPositiveDoubleAmount(txtNewRateUSD.getText()),
                        ErrorMessages.InvalidAmount));

        Validate.registerValidator(txtNewRateLKR,
                new CustomTextFieldValidationImpl(txtNewRateLKR, !fav.
                        validPositiveDoubleAmount(txtNewRateLKR.getText()),
                        ErrorMessages.InvalidAmount));
    }

}
