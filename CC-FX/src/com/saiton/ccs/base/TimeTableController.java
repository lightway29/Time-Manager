/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.base;

import com.saiton.ccs.basedao.ClassRegistrationDAO;
import com.saiton.ccs.basedao.TimeTableDAO;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lightway
 */
public class TimeTableController extends AnchorPane implements
        Initializable,
        Validatable, StagePassable {

    //<editor-fold defaultstate="collapsed" desc="Init">
    @FXML
    private ComboBox<String> cmbClass;
    @FXML
    private ComboBox<String> cmbTitle2;
    @FXML
    private ComboBox<String> cmbSlot;
    @FXML
    private ComboBox<String> cmbDay;
    @FXML
    private ComboBox<String> cmbTimeInterchange;
    @FXML
    private TableColumn<?, ?> tcDay;
    @FXML
    private TableColumn<?, ?> tcSlot;
    @FXML
    private TableColumn<?, ?> tcSubject;
    @FXML
    private TableColumn<?, ?> tcTeacher;
    @FXML
    private Button btnGenerateClassTimeTable;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnGenerateTeacherTimeTable;
    @FXML
    private TextField txtLabel;
    @FXML
    private Button btnSearchCustomer;
    @FXML
    private TableView<?> tvtime;

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
    TimeTableDAO timeTableDAO = new TimeTableDAO();
    private String userId;
    private String userName;
    private String userCategory;
    private boolean insert = false;
    private boolean update = false;
    private boolean delete = false;
    private boolean view = false;

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

    }

    @Override
    public void clearValidations() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void btnGenerateClassTimeTableOnAction(ActionEvent event) {
        
        
//        System.out.println("Slot for Art - "+timeTableDAO.isSlotAvailable("1", "mon",
//                                "Art","1"));
        
        
        // Classes 1
        // Subject all the subject go through
        // Teacher selected teacher
        ArrayList<String> classList = null;
        classList = timeTableDAO.getClassGroupList();

        ArrayList<String> dayList = new ArrayList();
        dayList.add("mon");
        dayList.add("tue");
        dayList.add("wed");
        dayList.add("thu");
        dayList.add("fri");

        ArrayList<String> subjectList = null;

        subjectList = timeTableDAO.getSubjectList();

        for (int classIndex = 0; classIndex < classList.size(); classIndex++) {
            System.out.println("----------------------");
            System.out.println("Class - " + classList.get(classIndex));
            System.out.println("----------------------");

            for (int dayIndex = 0; dayIndex < dayList.size(); dayIndex++) {

                for (int i = 1; i < 9; i++) {
                    String subject = "";
                    boolean isSubjectFound = false;

                    for (int subjectIndex = 0; subjectIndex < subjectList.size();
                            subjectIndex++) {

                        if (timeTableDAO.isSlotAvailable("1", dayList.get(
                                dayIndex),
                                subjectList.get(subjectIndex),i+"")) {
                            
                            subject = subjectList.get(subjectIndex);
                            subjectIndex = subjectList.size();
                        }

                    }
                    
                    System.out.println("Class - " + classList.get(classIndex)
                            + " Subject - " + subject
                            + " Day - " + dayList.get(dayIndex)
                            + " Slot availability - " + i);
                    subject = "";

                }

            }
        }

//        for each class {
//                for 5 days{
//                    for 8 periods{}
//                }
//                }     
                
    }

    @FXML
    private void btnSaveOnAction(ActionEvent event) {
    }

    @FXML
    private void btnCancelOnAction(ActionEvent event) {
    }

    @FXML
    private void btnGenerateTeacherTimeTableOnAction(ActionEvent event) {
    }
//</editor-fold>

    @FXML
    private void btnSearchCustomerOnAction(ActionEvent event) {
    }

}
