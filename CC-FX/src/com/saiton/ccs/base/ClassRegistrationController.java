/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.base;

import com.saiton.ccs.basedao.ClassRegistrationDAO;
import com.saiton.ccs.msgbox.MessageBox;
import com.saiton.ccs.msgbox.SimpleMessageBoxFactory;
import com.saiton.ccs.uihandle.ComponentControl;
import com.saiton.ccs.uihandle.StagePassable;
import com.saiton.ccs.uihandle.UiMode;
import com.saiton.ccs.util.InputDialog;
import com.saiton.ccs.validations.ErrorMessages;
import com.saiton.ccs.validations.FormatAndValidate;
import com.saiton.ccs.validations.MessageBoxTitle;
import com.saiton.ccs.validations.Validatable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lightway
 */
public class ClassRegistrationController extends AnchorPane implements
        Initializable,
        Validatable, StagePassable {

    //<editor-fold defaultstate="collapsed" desc="Init">
    @FXML
    private Insets x2;
    @FXML
    private Label lblItemId11;
    @FXML
    private Label lblItemId1;
    @FXML
    private Button btnAddSubGroup;
    @FXML
    private Label lblItemId12;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnClose;
    @FXML
    private ComboBox<String> cmbClass;
    @FXML
    private ComboBox<String> cmbSubGroup;
    @FXML
    private ComboBox<String> cmbGender;
    @FXML
    private Button btnAddClass;

    //</editor-fold>
    private final FormatAndValidate fav = new FormatAndValidate();
    private ComponentControl componentControl = new ComponentControl();
    private MessageBox mb;
    private Stage stage;
    private ObservableList<String> classData;
    private ObservableList<String> classSubGroupData;
    ObservableList<String> genderList = FXCollections.observableArrayList(
            "Male", "Female"
    );
    ClassRegistrationDAO classRegistrationDAO = new ClassRegistrationDAO();
    private String userId;
    private String userName;
    private String userCategory;
    private boolean insert = false;
    private boolean update = false;
    private boolean delete = false;
    private boolean view = false;
    @FXML
    private Button btnRemoveClass;

    //<editor-fold defaultstate="collapsed" desc="Methods">
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mb = SimpleMessageBoxFactory.createMessageBox();
    }

    @Override
    public void setStage(Stage stage, Object[] obj) {

        this.stage = stage;
        setUserAccessLevel();

        classData = cmbClass.getItems();
        classSubGroupData = cmbSubGroup.getItems();

        loadClassToCombobox();
        loadSubGroupToCombobox();

        cmbGender.setItems(genderList);
        cmbGender.getSelectionModel().selectFirst();

        validatorInitialization();
    }

    private void loadClassToCombobox() {

        cmbClass.setItems(null);
        ArrayList<String> classList = null;
        classList = classRegistrationDAO.loadClass();
        if (classList != null) {
            try {
                ObservableList<String> List = FXCollections.observableArrayList(
                        classList);
                cmbClass.setItems(List);
                cmbClass.setValue(List.get(0));
            } catch (Exception e) {

            }

        }

    }

    private void loadSubGroupToCombobox() {

        cmbSubGroup.setItems(null);
        ArrayList<String> subGroupList = null;
        subGroupList = classRegistrationDAO.loadSubGroup();
        if (subGroupList != null) {
            try {
                ObservableList<String> List = FXCollections.observableArrayList(
                        subGroupList);
                cmbSubGroup.setItems(List);
                cmbSubGroup.setValue(List.get(0));
            } catch (Exception e) {

            }

        }

    }

    private void setUserAccessLevel() {

        userId = UserSession.getInstance().getUserInfo().getEid();

        userName = UserSession.getInstance().getUserInfo().getName();
        userCategory = UserSession.getInstance().getUserInfo().getCategory();

        String title = stage.getTitle();

        if (!title.isEmpty()) {

            UserPermission userPermission = UserSession.getInstance().
                    findPermission(title);

            if (userPermission.canInsert() == true) {
                insert = true;
            }

            if (userPermission.canDelete() == true) {
                delete = true;
            }

            if (userPermission.canUpdate() == true) {
                update = true;
            }

            if (userPermission.canView() == true) {
                view = true;
            }

            if (insert == true && delete == true && update == true && view
                    == true) {
                setUiMode(UiMode.FULL_ACCESS);

            } else if (insert == false && delete == true && update == true
                    && view
                    == true) {
                setUiMode(UiMode.FULL_ACCESS);

            } else if (insert == true && delete == false && update == true
                    && view
                    == true) {
                setUiMode(UiMode.ALL_BUT_DELETE);

            } else if (insert == true && delete == true && update == false
                    && view
                    == true) {

                setUiMode(UiMode.FULL_ACCESS);

            } else if (insert == true && delete == true && update == true
                    && view
                    == false) {
                setUiMode(UiMode.SAVE);

            } else if (insert == false && delete == false && update == true
                    && view
                    == true) {

                setUiMode(UiMode.FULL_ACCESS);

            } else if (insert == false && delete == true && update == false
                    && view
                    == true) {
                setUiMode(UiMode.DELETE);

            } else if (insert == false && delete == true && update == true
                    && view
                    == false) {
                setUiMode(UiMode.NO_ACCESS);

            } else if (insert == true && delete == false && update == false
                    && view
                    == true) {

                setUiMode(UiMode.ALL_BUT_DELETE);

            } else if (insert == true && delete == false && update == true
                    && view
                    == false) {
                setUiMode(UiMode.SAVE);

            } else if (insert == true && delete == true && update == false
                    && view
                    == false) {
                setUiMode(UiMode.SAVE);

            } else if (insert == false && delete == false && update == false
                    && view
                    == true) {

                setUiMode(UiMode.READ_ONLY);

            } else if (insert == false && delete == false && update == true
                    && view
                    == false) {
                setUiMode(UiMode.NO_ACCESS);

            } else if (insert == false && delete == true && update == false
                    && view
                    == false) {
                setUiMode(UiMode.NO_ACCESS);

            } else if (insert == true && delete == false && update == false
                    && view
                    == false) {
                setUiMode(UiMode.SAVE);

            } else if (insert == false && delete == false && update == false
                    && view
                    == false) {
                setUiMode(UiMode.NO_ACCESS);

            }
        } else {

            setUiMode(UiMode.NO_ACCESS);

        }

    }

    private void disableUi(boolean state) {
        /*
         txtItemId.setDisable(state);
         txtItemId.setVisible(!state);

         txtItemName.setDisable(state);
         txtItemName.setVisible(!state);

         btnItemNameSearch.setDisable(state);
         btnItemNameSearch.setVisible(!state);

         txtUserId.setDisable(state);
         txtUserId.setVisible(!state);

         cmbBatchNo.setDisable(state);
         cmbBatchNo.setVisible(!state);

         btnBatchNo.setDisable(state);
         btnBatchNo.setVisible(!state);

         tblItemList.setDisable(state);
         tblItemList.setVisible(!state);

         btnDelete.setDisable(state);
         btnDelete.setVisible(!state);

         btnSave.setDisable(state);
         btnSave.setVisible(!state);

         btnClose.setDisable(state);
         btnClose.setVisible(!state);
         */
    }

    private void deactivateSearch() {

//        componentControl.deactivateSearch(lblItemName, txtItemName,
//                btnItemNameSearch,
//                220.00, 0.00);
    }

    private void deactivateCombo() {
//        componentControl.controlCComboBox(lblItemId1, cmbBatchNo, btnBatchNo,
//                220.00, 0.0, true);
    }

    private void setUiMode(UiMode uiMode) {
        /*
         switch (uiMode) {

         case SAVE:
         disableUi(false);
         deactivateSearch();

         btnDelete.setVisible(false);

         break;

         case DELETE:
         disableUi(false);

         btnSave.setDisable(true);
         btnSave.setVisible(false);

         deactivateCombo();

         break;

         case READ_ONLY:
         disableUi(false);
         deactivateCombo();
         btnDelete.setVisible(false);

         btnSave.setDisable(true);
         btnSave.setVisible(false);

         break;

         case ALL_BUT_DELETE:
         disableUi(false);

         btnDelete.setVisible(false);

         break;

         case FULL_ACCESS:
         disableUi(false);
         btnDelete.setVisible(false);
         break;

         case NO_ACCESS:
         disableUi(true);

         break;

         }
         */
    }

    private void validatorInitialization() {
        /*
         validationSupportTableData.registerValidator(txtItemName,
         new CustomTextAreaValidationImpl(txtItemName,
         !fav.validName(txtItemName.getText()),
         ErrorMessages.Error));

         validationSupportTableData.registerValidator(txtBuyingPrice,
         new CustomTextFieldValidationImpl(txtBuyingPrice,
         !fav.chkPrice(txtBuyingPrice.getText()),
         ErrorMessages.InvalidPrice));
        
         validationSupportTableData.registerValidator(txtQty,
         new CustomTextFieldValidationImpl(txtQty,
         !fav.chkPrice(txtQty.getText()),
         ErrorMessages.InvalidQty));
        
         validationSupportTableData.registerValidator(txtQty,
         new CustomTextFieldValidationImpl(txtQty,
         !fav.chkPrice(txtQty.getText()),
         ErrorMessages.InvalidQty));
        
         validationSupportTableData.registerValidator(txtSellingPrice,
         new CustomTextFieldValidationImpl(txtSellingPrice,
         !fav.chkPrice(txtSellingPrice.getText()),
         ErrorMessages.InvalidPrice));

         validationSupportTable.registerValidator(tblItemList,
         new CustomTableViewValidationImpl(tblItemList,
         !fav.validTableView(tblItemList),
         ErrorMessages.EmptyListView));
         */
    }

    @Override
    public boolean isValid() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void clearInput() {
        cmbClass.getSelectionModel().selectFirst();
        cmbSubGroup.getSelectionModel().selectFirst();
        cmbGender.getSelectionModel().selectFirst();

    }

    @Override
    public void clearValidations() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="ActionEvents">
    
    @FXML
    private void btnRemoveClassOnAction(ActionEvent event) {
       boolean isDeleted = classRegistrationDAO.deleteClass(
                                    cmbClass.getValue());

                            if (isDeleted == true) {

                                mb.ShowMessage(stage,
                                        ErrorMessages.SuccesfullyDeleted,
                                        MessageBoxTitle.INFORMATION.toString(),
                                        MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                                        MessageBox.MessageType.MSG_OK);
                                loadClassToCombobox();
                                loadSubGroupToCombobox();

                            } else {
                                mb.ShowMessage(stage, ErrorMessages.Error,
                                        MessageBoxTitle.ERROR.toString(),
                                        MessageBox.MessageIcon.MSG_ICON_FAIL,
                                        MessageBox.MessageType.MSG_OK);
                            }
    }

    @FXML
    private void cmbSubGroupOnAction(ActionEvent event) {

    }

    @FXML
    private void cmbGenderOnAction(ActionEvent event) {
    }

    @FXML
    private void btnAddSubGroupOnAction(ActionEvent event) {
        String subGroup = InputDialog.inputForAddNew("Sub Group");
        boolean isSaved = false;
        if (subGroup == null) {
            return;
        }
        if (!fav.validName(subGroup)) {
            mb.ShowMessage(stage, "Invalid Class", "Sub Group",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }

        if (classData.contains(subGroup)) {
            mb.ShowMessage(stage, "Duplicate Sub Group", "Sub Group",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }

        isSaved = classRegistrationDAO.insertSubGroup(subGroup);

        if (isSaved == false) {
            mb.ShowMessage(stage, "Data not saved.", "Class",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }

        //success
        classSubGroupData.add(subGroup);
        loadSubGroupToCombobox();
        cmbSubGroup.getSelectionModel().select(subGroup);

        validatorInitialization();
    }

    @FXML
    private void btnSaveOnAction(ActionEvent event) {
        boolean isClassGroupInserted = false;

        isClassGroupInserted = classRegistrationDAO.insertClassGroup(
                classRegistrationDAO.getClassId(cmbClass.getValue()),
                classRegistrationDAO.getSubGroupId(cmbSubGroup.getValue()),
                cmbGender.getValue()
        );

        if (isClassGroupInserted == true) {

            mb.ShowMessage(stage, "Data saved.",
                    MessageBoxTitle.INFORMATION.toString(),
                    MessageBox.MessageIcon.MSG_ICON_SUCCESS,
                    MessageBox.MessageType.MSG_OK);

//                    mb.ShowMessage(stage, "Data saved.", "Class",
//                    MessageBox.MessageIcon.MSG_ICON_FAIL,
//                    MessageBox.MessageType.MSG_OK);
            clearInput();

        } else {
            mb.ShowMessage(stage, ErrorMessages.NotSuccesfullyCreated,
                    MessageBoxTitle.ERROR.toString(),
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
        }

    }

    @FXML
    private void btnCloseOnAction(ActionEvent event) {
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cmbClassOnAction(ActionEvent event) {

    }

    @FXML
    private void btnAddClassOnAction(ActionEvent event) {

        String classTitle = InputDialog.inputForAddNew("Class");
        boolean isSaved = false;
        if (classTitle == null) {
            return;
        }
        if (!fav.validName(classTitle)) {
            mb.ShowMessage(stage, "Invalid Class", "Class",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }

        if (classData.contains(classTitle)) {
            mb.ShowMessage(stage, "Duplicate Class", "Class",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }

        isSaved = classRegistrationDAO.insertClass(classTitle);

        if (isSaved == false) {
            mb.ShowMessage(stage, "Data not saved.", "Class",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }

        //success
        classData.add(classTitle);
        loadClassToCombobox();
        cmbClass.getSelectionModel().select(classTitle);

        validatorInitialization();

    }
//</editor-fold>

    @FXML
    void cmbClassOnKeyReleased(KeyEvent event) {
         if (event.getCode() == KeyCode.DELETE) {
             System.out.println("Delete executed...");
         }
    }

   
}
