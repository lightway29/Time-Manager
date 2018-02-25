package com.saiton.ccs.base;

import com.saiton.ccs.uihandle.StagePassable;
import com.saiton.ccs.msgbox.MessageBox;
import com.saiton.ccs.msgbox.SimpleMessageBoxFactory;
//import com.saiton.ccs.ui;
import com.saiton.ccs.validations.FormatAndValidate;
import com.saiton.ccs.validations.Validatable;
import com.saiton.ccs.database.DB;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;


public class DatabaseConController extends AnchorPane implements Initializable,
        Validatable, StagePassable {

    @FXML
    private ToggleGroup Host;

    @FXML
    private Label lblStatus; //------------> Status

    @FXML
    private PasswordField passPassword;

    @FXML
    private ProgressBar prgProgress; //-------> Progess

    @FXML
    private RadioButton radIP;

    @FXML
    private RadioButton radName;

    @FXML
    private TextField txtDatabase;

    @FXML
    private TextField txtHostname;

    @FXML
    private TextField txtIPPart1;

    @FXML
    private TextField txtIPPart2;

    @FXML
    private TextField txtIPPart3;

    @FXML
    private TextField txtIPPart4;

    @FXML
    private TextField txtPort;

    @FXML
    private TextField txtUsername;

    @FXML
    private CheckBox chkFinger;
    
    @FXML
    private CheckBox chkServer;

    private Stage stage;

    private ValidationSupport validationSupport;
    private ValidationSupport validationSupportHost;
    private ValidationSupport validationSupportIP;
    private FormatAndValidate fav;

    @FXML
    private Insets x1;

    @FXML
    void CancelAction(ActionEvent event) {
        if (SimpleMessageBoxFactory.createMessageBox().ShowMessage(stage,
                "Are you sure you want to exit ?",
                "Database Connection", MessageBox.MessageIcon.MSG_ICON_FAIL,
                MessageBox.MessageType.MSG_YESNO)
                == MessageBox.MessageOutput.MSG_YES) {
            Platform.exit();
            System.exit(0);
        }
    }

    private void showErrorMessage() {
        StringBuilder sb = new StringBuilder();
        ValidationResult v = validationSupport.getValidationResult();
        for (ValidationMessage m : v.getErrors()) {
            sb.append(m.getText()).append("\n");
        }

        if (radIP.isSelected()) {
            v = validationSupportIP.getValidationResult();
            for (ValidationMessage m : v.getErrors()) {
                sb.append(m.getText()).append("\n");
            }
        } else {
            v = validationSupportHost.getValidationResult();
            for (ValidationMessage m : v.getErrors()) {
                sb.append(m.getText()).append("\n");
            }
        }

        SimpleMessageBoxFactory.createMessageBox().ShowMessage(
                stage, sb.toString(),
                "Database Connection");
    }

    @FXML
    void OKAction(ActionEvent event) {
        if (isValid()) {
            //Test
            prgProgress.setProgress(0.5);
            String username = txtUsername.getText();
            String password = passPassword.getText();
            String ip, hostname, host;
            if (radIP.isSelected()) {
                ip = String.format("%s.%s.%s.%s", txtIPPart1.getText(),
                        txtIPPart2.getText(), txtIPPart3.getText(), txtIPPart4.
                        getText());
                host = ip;
                hostname = "";
            } else {
                hostname = txtHostname.getText();
                host = hostname;
                ip = "";
            }

            String port = txtPort.getText();
            String database = txtDatabase.getText();
            boolean success = DB.check(username, password, host, database,
                    port);
            prgProgress.setProgress(1);

            lblStatus.setText(success ? "Success" : "Failiure");

            if (success) {
                ApplicationProperties app = ApplicationProperties.getInstance();
                app.setDataSaved(true);
                app.setFingerAvailable(chkFinger.isSelected());
                app.setDatabase(database);
                app.setHostname(hostname);
                app.setIp(ip);
                app.setPassword(password);
                app.setPort(port);
                app.setUsername(username);
                app.save();

                SimpleMessageBoxFactory.createMessageBox().ShowMessage(stage,
                        "Settings Saved", "Database Connection");
                stage.close();
            } else {
                SimpleMessageBoxFactory.createMessageBox().ShowMessage(stage,
                        "Test Failed", "Database Connection",
                        MessageBox.MessageIcon.MSG_ICON_FAIL,
                        MessageBox.MessageType.MSG_OK);
            }
        } else {
            showErrorMessage();
        }
    }

    @FXML
    void TestAction(ActionEvent event) {
        if (isValid()) {
            //Test
            prgProgress.setProgress(0.5);
            String username = txtUsername.getText();
            String password = passPassword.getText();
            String ip, hostname, host;
            if (radIP.isSelected()) {
                ip = String.format("%s.%s.%s.%s", txtIPPart1.getText(),
                        txtIPPart2.getText(), txtIPPart3.getText(), txtIPPart4.
                        getText());
                host = ip;
            } else {
                hostname = txtHostname.getText();
                host = hostname;
            }

            String port = txtPort.getText();
            String database = txtDatabase.getText();
            boolean success = DB.check(username, password, host, database,
                    port);
            prgProgress.setProgress(1);

            lblStatus.setText(success ? "Success" : "Failiure");

        } else {
            showErrorMessage();
        }
    }

    @FXML
    void hostnameSelected(ActionEvent event) {
        switchHostIP();
    }

    @FXML
    void ipSelected(ActionEvent event) {
        switchHostIP();
    }
    
    @FXML
    void chkServerOnAction(ActionEvent event){
    
    }

    private void switchHostIP() {
        if (radIP.isSelected()) {
            txtHostname.setDisable(true);
            txtIPPart1.setDisable(false);
            txtIPPart2.setDisable(false);
            txtIPPart3.setDisable(false);
            txtIPPart4.setDisable(false);
        } else {
            txtHostname.setDisable(false);
            txtIPPart1.setDisable(true);
            txtIPPart2.setDisable(true);
            txtIPPart3.setDisable(true);
            txtIPPart4.setDisable(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validationSupport = new ValidationSupport();

        validationSupportHost = new ValidationSupport(); //Hostname
        validationSupportIP = new ValidationSupport(); //IP

        fav = FormatAndValidate.getInstance();

        switchHostIP();

        validationSupport.registerValidator(txtDatabase, Validator.
                createEmptyValidator("Database should be mentioned"));
        validationSupport.registerValidator(txtUsername, Validator.
                createEmptyValidator("Username should be mentioned"));
        validationSupport.registerValidator(passPassword, Validator.
                createEmptyValidator("Password should be mentioned"));

        validationSupport.registerValidator(txtPort,
                (Control c, String newValue)
                -> ValidationResult.fromErrorIf(txtPort,
                        "Port should be a number", !(fav.isInteger(newValue))));

        validationSupportHost.registerValidator(txtHostname, Validator.
                createEmptyValidator("Hostname should be mentioned"));

        validationSupportIP.registerValidator(txtIPPart1, (Control c,
                String newValue)
                -> ValidationResult.fromErrorIf(txtIPPart1,
                        "IP Address portion should be a number", !(fav.
                        isInteger(newValue))));
        validationSupportIP.registerValidator(txtIPPart2, (Control c,
                String newValue)
                -> ValidationResult.fromErrorIf(txtIPPart2,
                        "IP Address portion should be a number", !(fav.
                        isInteger(newValue))));
        validationSupportIP.registerValidator(txtIPPart3, (Control c,
                String newValue)
                -> ValidationResult.fromErrorIf(txtIPPart3,
                        "IP Address portion should be a number", !(fav.
                        isInteger(newValue))));
        validationSupportIP.registerValidator(txtIPPart4, (Control c,
                String newValue)
                -> ValidationResult.fromErrorIf(txtIPPart4,
                        "IP Address portion should be a number", !(fav.
                        isInteger(newValue))));

        lblStatus.setText("");
    }

    @Override
    public boolean isValid() {
        boolean ret = !validationSupport.isInvalid();
        if (radIP.isSelected()) {
            //IP
            ret = ret && !validationSupportIP.isInvalid();
        } else {
            ret = ret && !validationSupportHost.isInvalid();
        }

        return ret;
    }

    @Override
    public void clearInput() {
        txtDatabase.setText("");
        txtHostname.setText("");
        txtIPPart1.setText("");
        txtIPPart2.setText("");
        txtIPPart3.setText("");
        txtIPPart4.setText("");
        txtPort.setText("");
        txtUsername.setText("");
        passPassword.setText("");
    }

    @Override
    public void clearValidations() {
        validationSupport.redecorate();
    }

    @Override
    public void setStage(Stage stage, Object[] obj) {
        this.stage = stage;
        stage.setOnCloseRequest(event -> {
            if (SimpleMessageBoxFactory.createMessageBox().ShowMessage(stage,
                    "Are you sure you want to exit ?",
                    "Database Connection", MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_YESNO)
                    == MessageBox.MessageOutput.MSG_YES) {
                Platform.exit();
                System.exit(0);
            } else {
                event.consume();
            }
        });

    }
    
    

}
