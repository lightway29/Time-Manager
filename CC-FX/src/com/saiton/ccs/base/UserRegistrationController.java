package com.saiton.ccs.base;

import com.saiton.ccs.basedao.UserDAO;
import com.saiton.ccs.finger.FingerFxUtil;
import com.saiton.ccs.finger.FingerPrintDetect;
import com.saiton.ccs.finger.FingerPrintReceive;
import com.saiton.ccs.msgbox.MessageBox;
import com.saiton.ccs.msgbox.SimpleMessageBoxFactory;
import com.saiton.ccs.uihandle.CheckBoxTableCell;
import com.saiton.ccs.uihandle.StagePassable;
import com.saiton.ccs.util.InputDialog;
import com.saiton.ccs.validations.CustomTextFieldValidationImpl;
import com.saiton.ccs.validations.ErrorMessages;
import com.saiton.ccs.validations.FormatAndValidate;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

public class UserRegistrationController implements StagePassable, Initializable,
        FingerPrintReceive {

    @FXML
    private ImageView imgFinger;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtUserName;

    @FXML
    private ToggleGroup tgFinger;

    @FXML
    private TextField txtName;

    @FXML
    private ComboBox<String> cmbTitle;

    @FXML
    private RadioButton rdbWithFingerPrint;

    @FXML
    private RadioButton rdbWithoutFingerPrint;

    @FXML
    private ComboBox<String> cmbUserType;

    @FXML
    private ComboBox<String> cmbUserSubType;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnSearchUser;

    @FXML
    private Button btnDelete;

    private Stage stage;

    private final MessageBox messageBox
            = SimpleMessageBoxFactory.createMessageBox();

    private final UserDAO userDao = new UserDAO();

    private final FormatAndValidate fav = new FormatAndValidate();

    private FingerPrintDetect fpd;

    private boolean isUpdate = false;
    //------------------------------------------------------
    //UI PERMISSION TABLE
    @FXML
    private TableView<UserPermission> tvPermission;

    @FXML
    private TableColumn<UserPermission, String> tcFeature;

    @FXML
    private TableColumn<UserPermission, Boolean> tcInsert;

    @FXML
    private TableColumn<UserPermission, Boolean> tcUpdate;

    @FXML
    private TableColumn<UserPermission, Boolean> tcDelete;

    @FXML
    private TableColumn<UserPermission, Boolean> tcView;

    private ObservableList<UserPermission> permissionsData;

    //-----------------------------------------------------
    // NOTIFICATIONS TABLE
    @FXML
    private TableColumn<UserNotification, String> tcNotification;

    @FXML
    private TableColumn<UserNotification, Boolean> tcShow;

    @FXML
    private TableView<UserNotification> tvNotifications;

    private ObservableList<UserNotification> notificationsData;
    //-------------------------------------------------------
    //  USER TABLE
    PopOver userPop;
    TableView<UserInfo> tvUser;
    ObservableList<UserInfo> usersData;

    private void createPop() {
        tvUser = new TableView<>();
        TableColumn<UserInfo, String> eid = new TableColumn<>("User ID");
        eid.setMinWidth(50.0);
        TableColumn<UserInfo, String> title = new TableColumn<>("Title");
        title.setMinWidth(50.0);
        TableColumn<UserInfo, String> name = new TableColumn<>("Name");
        name.setMinWidth(50.0);
        TableColumn<UserInfo, String> username = new TableColumn<>("Username");
        username.setMinWidth(50.0);
        TableColumn<UserInfo, String> flag = new TableColumn<>("Profession");
        flag.setMinWidth(50.0);
        TableColumn<UserInfo, String> category = new TableColumn<>("Category");
        category.setMinWidth(50.0);
//        TableColumn<UserInfo, Boolean> finger
//                = new TableColumn<>("Finger Print");
//        finger.setMinWidth(50.0);

        eid.setCellValueFactory(
                new PropertyValueFactory("eid"));

        title.setCellValueFactory(
                new PropertyValueFactory("title"));

        name.setCellValueFactory(
                new PropertyValueFactory("name"));

        username.setCellValueFactory(
                new PropertyValueFactory("username"));

        flag.setCellValueFactory(
                new PropertyValueFactory("flag"));

        category.setCellValueFactory(
                new PropertyValueFactory("category"));

//        finger.setCellValueFactory(
//                new PropertyValueFactory("finger"));

     //   finger.setCellFactory((column) -> new CheckBoxTableCell<>());

        tvUser.getColumns().addAll(eid, title, name, username, flag, category);

        usersData = tvUser.getItems();
        usersData.clear();
        userPop = new PopOver(tvUser);

        tvUser.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1 && (e.getButton()
                    == MouseButton.SECONDARY)) {
                userPop.hide();

            } else if (e.getClickCount() == 2) {
                try {
                    UserInfo p = null;
                    p = tvUser.getSelectionModel().getSelectedItem();

                    if (p != null && p.eidProperty().get() != null) {
                        setUpdateMode();
                        txtEmployeeId.setText(p.eidProperty().get());
                        txtPassword.setText("");
                        txtConfirmPassword.setText("");
                        txtName.setText(p.nameProperty().get());
                        txtUserName.setText(p.usernameProperty().get());
                        cmbTitle.getSelectionModel().select(p.titleProperty().
                                get());
                        cmbUserType.getSelectionModel().select(p.flagProperty().
                                get());
                        cmbUserSubType.getSelectionModel().select(p.
                                categoryProperty().get());
//                        if (p.fingerProperty().get()) {
//                            rdbWithFingerPrint.setSelected(true);
//                        } else {
//                            rdbWithoutFingerPrint.setSelected(true);
//                        }
                        loadPermissions(p.eidProperty().get());
                    }

                } catch (NullPointerException n) {
                    n.printStackTrace();
                }

                userPop.hide();
                validate();
            }
        });
    }

    //-------------------------------------------------------
    ValidationSupport vsBase = new ValidationSupport();

    private void setUpdateMode() {
        isUpdate = true;
        btnRegister.setVisible(false);
        btnUpdate.setVisible(true);
        btnDelete.setVisible(true);
    }

    private void setRegisterMode() {
        isUpdate = false;
        btnRegister.setVisible(true);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearAll();
    }

    @FXML
    void btnSearchUserOnAction(ActionEvent event) {
        usersData.clear();
        List<String[]> data = userDao.selectAllUsers();
        List<UserInfo> users = new ArrayList<>();
        for (String atom[] : data) {
            UserInfo u = new UserInfo();
            u.setFromStringArray(atom);
            users.add(u);
        }
        usersData.addAll(users);
        if (!userPop.isShowing()) {
            userPop.show(btnSearchUser);
        }
    }

    @FXML
    void btnAddUserTypeOnAction(ActionEvent event) {
        String userType = InputDialog.inputForAddNew("Position");

        if (userType != null && fav.validProfession(userType)) {

            if (userDao.hasUserType(userType)) {
                messageBox.ShowMessage(stage, "Duplicate found", "User",
                        MessageBox.MessageIcon.MSG_ICON_FAIL,
                        MessageBox.MessageType.MSG_OK);
                return;
            }

            if (userDao.insertUserType(userType)) {
                messageBox.ShowMessage(stage, "Position added", "User",
                        MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                        MessageBox.MessageType.MSG_OK);
                loadUserTypes();
            }
        } else {
            messageBox.ShowMessage(stage, "Position not added", "User",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
        }

    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
//        if (userPop.isShowing()) {
//            userPop.hide();
//        } else {
//            fpd.destroy();
            stage.close();
//        }
    }

    public void addUser() {
        if (vsBase.isInvalid()) {
            messageBox.
                    ShowMessage(stage, "Please fill the form with valid data.",
                            "User",
                            MessageBox.MessageIcon.MSG_ICON_FAIL,
                            MessageBox.MessageType.MSG_OK);
            return;
        }
//        byte[] finger = null;
//        Boolean hasFinger = false;
//        if (rdbWithFingerPrint.isSelected()) {
//            //finger print
//            finger = fpd.getFingerPrintData();
//            hasFinger = true;
//        }

//        if (finger == null && rdbWithFingerPrint.isSelected()) {
//            messageBox.ShowMessage(stage, "Need to scan for a fingerprint.",
//                    "User",
//                    MessageBox.MessageIcon.MSG_ICON_FAIL,
//                    MessageBox.MessageType.MSG_OK);
//            return;
//        }

        if (!isUpdate && userDao.hasUsername(txtUserName.getText())) {
            messageBox.ShowMessage(stage, "Username already exists.",
                    "User",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }

        List<String[]> notifcs = new ArrayList<>();
        for (UserNotification n : notificationsData) {
            notifcs.add(n.toStringArray());
        }

        List<String[]> perms = new ArrayList<>();
        for (UserPermission p : permissionsData) {
            perms.add(p.toStringArray());
        }

        String eid = userDao.generateEmpID();
        if (eid != null && (userDao.insertUpdateUser(isUpdate, isUpdate
                ? txtEmployeeId.getText() : eid, cmbTitle.
                getValue(),
                txtName.getText(), txtUserName.getText(), txtPassword.getText(),
                cmbUserType.getValue(), cmbUserSubType.
                getValue())) && userDao.setUserPermissions(isUpdate
                                ? txtEmployeeId.getText() : eid, perms)
                && userDao.setUserNotifications(isUpdate
                                ? txtEmployeeId.getText() : eid, notifcs)) {
            messageBox.ShowMessage(stage, "Success.",
                    "User",
                    MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                    MessageBox.MessageType.MSG_OK);
            clearAll();
        } else {
            messageBox.ShowMessage(stage, "Failed.",
                    "User",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
        }
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        addUser();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        addUser();
    }

    @FXML
    void cmbUserSubTypeOnAction(ActionEvent event) {
        //admin select everything
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (isUpdate && !txtEmployeeId.getText().isEmpty() && messageBox.
                ShowMessage(stage,
                        "Are you sure you want to disable this user ?"
                        + " You cannot enable this user again.",
                        "User",
                        MessageBox.MessageIcon.MSG_ICON_FAIL,
                        MessageBox.MessageType.MSG_YESNO)
                == MessageBox.MessageOutput.MSG_YES) {
            userDao.deleteUser(txtEmployeeId.getText());
            clearAll();
        }

    }

    @FXML
    void txtEmployeeIdOnKeyReleased(KeyEvent event) {
        validate();
    }

    @FXML
    void txtNameOnKeyReleased(KeyEvent event) {
        validate();
    }

    @FXML
    void txtUserNameOnKeyReleased(KeyEvent event) {
        validate();
    }

    @FXML
    void txtPasswordOnKeyReleased(KeyEvent event) {
        validate();
    }

    @FXML
    void txtConfirmPasswordOnKeyReleased(KeyEvent event) {
        validate();
    }

    @Override
    public void setStage(Stage stage, Object[] obj) {
        this.stage = stage;
        stage.setHeight(620.0);
        stage.setWidth(710.0);

//        fpd = new FingerPrintDetect(this);
//        stage.setOnCloseRequest(event -> {
//            if (userPop.isShowing()) {
//                event.consume();
//                userPop.hide();
//            } else {
//                fpd.destroy();
//            }
//
//        });
        validate();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //--------------------------------
        // Permissions
        tvPermission.setEditable(true);
        permissionsData = tvPermission.getItems();

        tcFeature.setCellValueFactory(new PropertyValueFactory("type"));

        tcInsert.setCellValueFactory(
                new PropertyValueFactory("insert"));

        tcUpdate.setCellValueFactory(
                new PropertyValueFactory("update"));

        tcDelete.setCellValueFactory(
                new PropertyValueFactory("delete"));

        tcView.setCellValueFactory(
                new PropertyValueFactory("view"));

        tcInsert.setCellFactory((column) -> new CheckBoxTableCell<>());

        tcUpdate.setCellFactory((column) -> new CheckBoxTableCell<>());

        tcDelete.setCellFactory((column) -> new CheckBoxTableCell<>());

        tcView.setCellFactory((column) -> new CheckBoxTableCell<>());

        //------------------------------
        // Notificatrions
        tvNotifications.setEditable(true);

        notificationsData = tvNotifications.getItems();

        tcNotification.setCellValueFactory(
                new PropertyValueFactory("type"));

        tcShow.setCellValueFactory(
                new PropertyValueFactory("show"));

        tcShow.setCellFactory((column) -> new CheckBoxTableCell<>());

        createPop();
        //---------------------
        clearAll();
    }

    private void loadPermissions(String eid) {
        permissionsData.clear();
        notificationsData.clear();
        List<String[]> p = userDao.selectPermissions(eid);
        
        for (String[] data : p) {
            UserPermission up = new UserPermission();
            up.setFromStringArray(data);
            permissionsData.add(up);
        }
        List<String[]> n = userDao.selectNotifications(eid);
        for (String[] data : n) {
            UserNotification un = new UserNotification();
            un.setFromStringArray(data);
            notificationsData.add(un);
        }
    }

    private void clearPermissions() {
        loadPermissions(null);
    }

    /**
     * clear all data
     */
    private void clearAll() {
        clearPermissions();
        txtEmployeeId.setText(userDao.generateEmpID());
        txtUserName.setText("");
        txtName.setText("");
        txtPassword.setText("");
        txtConfirmPassword.setText("");
        cmbTitle.getSelectionModel().selectFirst();
        loadUserTypes();
        cmbUserSubType.getItems().clear();
        cmbUserSubType.getItems().addAll(userDao.getUserSubTypes());
        cmbUserSubType.getSelectionModel().selectFirst();
//        rdbWithFingerPrint.setSelected(true);
        setRegisterMode();
        validate();
    }

    private void loadUserTypes() {
        cmbUserType.getItems().clear();
        cmbUserType.getItems().addAll(userDao.getUserTypes());
        cmbUserType.getSelectionModel().selectFirst();
    }

    @Override
    public void setImage(BufferedImage img) {
        Platform.runLater(() -> {
            try {
                imgFinger.setImage(FingerFxUtil.createImage(img));
            } catch (IOException ex) {

            }
        });
    }

    @Override
    public void writeLog(String str) {
     
    }

    private void validate() {
        vsBase.registerValidator(txtName,
                new CustomTextFieldValidationImpl(txtName, !fav.
                        validHallName(txtName.getText()),
                        ErrorMessages.InvalidName));

        vsBase.registerValidator(txtEmployeeId, Validator.createEmptyValidator(
                ErrorMessages.Empty));

        vsBase.registerValidator(txtUserName,
                new CustomTextFieldValidationImpl(txtUserName, !fav.
                        isValidUsername(txtUserName.getText()),
                        ErrorMessages.InvalidUsername));

        vsBase.registerValidator(txtPassword,
                new CustomTextFieldValidationImpl(txtPassword,
                        !txtPassword.getText().equals(
                                txtConfirmPassword.getText()),
                        ErrorMessages.PasswordsMismatch));

        vsBase.registerValidator(txtConfirmPassword,
                new CustomTextFieldValidationImpl(txtConfirmPassword,
                        !txtConfirmPassword.getText().equals(
                                txtPassword.getText()),
                        ErrorMessages.PasswordsMismatch));

        vsBase.registerValidator(cmbUserType, Validator.createEmptyValidator(
                ErrorMessages.Empty));
    }

    @Override
    public void fingerPrintExceptionOccured(Exception ex) {
        Platform.runLater(() -> {
            try {
                messageBox.ShowMessage(stage,
                        "Finger print reader cannot initialize.\n"
                        + "Please use a machine with a configured fingerprint reader to register a user\n",
                        "Login", MessageBox.MessageIcon.MSG_ICON_FAIL,
                        MessageBox.MessageType.MSG_OK);
                stage.close();
            } catch (Exception e) {
            }
        });

    }
}
