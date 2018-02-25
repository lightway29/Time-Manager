package com.saiton.ccs.base;

import com.saiton.ccs.msgbox.MessageBox;
import com.saiton.ccs.msgbox.SimpleMessageBoxFactory;
import com.saiton.ccs.uihandle.StagePassable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Login Controller
 *
 * @author Saiton
 */
public class LoginNoFingerController extends AnchorPane implements
        StagePassable, LoginAccess {

    @FXML
    private PasswordField passPassword;

    @FXML
    private TextField txtUsername;

    private Stage stage;

    private final MessageBox mb = SimpleMessageBoxFactory.createMessageBox();

    private HomeCallback home;

    @FXML
    void CancelAction(ActionEvent event) {
        if (mb.ShowMessage(stage, "Are you sure you want to exit ?", "Login",
                MessageBox.MessageIcon.MSG_ICON_NONE,
                MessageBox.MessageType.MSG_YESNO)
                == MessageBox.MessageOutput.MSG_YES) {
            Platform.exit();
        }
    }

    @FXML
    void passOnKeyReleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            login();
        }
    }

    private void login() {
        if (UserSession.getInstance().
                login(txtUsername.getText(), passPassword.getText())) {
            clearAll();
            home.startHomeUi();
            home.onLoginSuccess();
        } else {
            mb.ShowMessage(stage, "Login failed.", "Login",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            passPassword.clear();          
        }
    }

    @FXML
    void LoginAction(ActionEvent event) {
        login();
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        if (mb.ShowMessage(stage,
                "Do you want to reset login mode ?\n"
                + "This will close the application,Please start again.",
                "Login", MessageBox.MessageIcon.MSG_ICON_FAIL,
                MessageBox.MessageType.MSG_YESNO)
                == MessageBox.MessageOutput.MSG_NO) {
            return;
        }
        ApplicationProperties.getInstance().reset();
        mb.ShowMessage(stage,
                "Login Reset Success.\nApplication will now close.", "Login");
        Platform.exit();
    }

    public void clearAll() {
        passPassword.setText("");
        txtUsername.setText("");
    }

    @Override
    public void setStage(Stage stage, Object[] obj) {
        this.stage = stage;
        home = (HomeCallback) obj[0];
        home.setLoginAccess(this);
    }

    @Override
    public void init() {
        clearAll();
    }

}
