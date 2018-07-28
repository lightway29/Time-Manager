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
import com.saiton.ccs.popup.TimeTablePopup;
import com.saiton.ccs.uihandle.ComponentControl;
import com.saiton.ccs.uihandle.ReportGenerator;
import com.saiton.ccs.uihandle.StagePassable;
import com.saiton.ccs.uihandle.UiMode;
import com.saiton.ccs.util.InputDialog;
import com.saiton.ccs.validations.ErrorMessages;
import com.saiton.ccs.validations.FormatAndValidate;
import com.saiton.ccs.validations.MessageBoxTitle;
import com.saiton.ccs.validations.Validatable;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;

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
    private ComboBox<String> cmbSlot;
    @FXML
    private ComboBox<String> cmbDay;
    @FXML
    private ComboBox<String> cmbSubject;
    @FXML
    private ComboBox<String> cmbTeacher;
    @FXML
    private TableColumn<Timetable, String> tcDay;
    @FXML
    private TableColumn<Timetable, String> tcSlot;
    @FXML
    private TableColumn<Timetable, String> tcSubject;
    @FXML
    private TableColumn<Timetable, String> tcTeacher;
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
    private TableView<Timetable> tvtime;

    //</editor-fold>
    private final FormatAndValidate fav = new FormatAndValidate();
    private ComponentControl componentControl = new ComponentControl();
    private MessageBox mb;
    private Stage stage;
    private ObservableList TableTimetableData = FXCollections.
            observableArrayList();
    private ObservableList<String> classData;
    private ObservableList<String> classSubGroupData;
    ObservableList<String> genderList = FXCollections.observableArrayList(
            "Male", "Female"
    );
    ClassRegistrationDAO classRegistrationDAO = new ClassRegistrationDAO();
    TimeTableDAO timeTableDAO = new TimeTableDAO();
    Timetable timeTable = new Timetable();
    private String userId;
    private String userName;
    private String userCategory;
    private boolean insert = false;
    private boolean update = false;
    private boolean delete = false;
    private boolean view = false;

    //Timetable Popup
    private TableView timetableIdTable = new TableView();
    private TimeTablePopup timetableIdPopup = new TimeTablePopup();
    private ObservableList timetableData = FXCollections.
            observableArrayList();
    private PopOver timetableIdPop;
    String timetableId = "";
    ObservableList<String> dayList = FXCollections.observableArrayList(
            "Mon", "Tue", "Wed", "Thu", "Fri"
    );

    ObservableList<String> slotList = FXCollections.observableArrayList(
            "1", "2", "3", "4", "5", "6", "7", "8"
    );

    //<editor-fold defaultstate="collapsed" desc="Methods">
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mb = SimpleMessageBoxFactory.createMessageBox();
        loadClassToCombobox();
        loadSubjectToCombobox();
        loadTeacherToCombobox();
        loadGrnData();

        cmbDay.setItems(dayList);
        cmbDay.getSelectionModel().selectFirst();
        cmbSlot.setItems(slotList);
        cmbSlot.getSelectionModel().selectFirst();

        tcDay.setCellValueFactory(new PropertyValueFactory<Timetable, String>(
                "colDay"));
        tcSlot.setCellValueFactory(
                new PropertyValueFactory<Timetable, String>(
                        "colSlot"));
        tcSubject.setCellValueFactory(
                new PropertyValueFactory<Timetable, String>(
                        "colSubject"));
        tcTeacher.setCellValueFactory(
                new PropertyValueFactory<Timetable, String>(
                        "colTeacherIncharge"));

        tvtime.setItems(TableTimetableData);
    }

    @Override
    public void setStage(Stage stage, Object[] obj) {

        this.stage = stage;
        setUserAccessLevel();

        //CustomerId popup------------------------
        timetableIdTable = timetableIdPopup.tableViewLoader(timetableData);

        timetableIdTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                try {
                    TimeTablePopup p = null;
                    p = (TimeTablePopup) timetableIdTable.getSelectionModel().
                            getSelectedItem();
                    //clearInput();

                    if (p.getColTimetableId() != null) {
                        txtLabel.setText(p.getColTitle());
                        timetableId = p.getColTimetableId();

                    }

                } catch (NullPointerException n) {

                }

                timetableIdPop.hide();
                validatorInitialization();

            }

        });

        timetableIdTable.setOnMousePressed(e -> {

            if (e.getButton() == MouseButton.SECONDARY) {

                timetableIdPop.hide();
                validatorInitialization();

            }

        });

        timetableIdPop = new PopOver(timetableIdTable);

        stage.setOnCloseRequest(e -> {

            if (timetableIdPop.isShowing()) {
                e.consume();
                timetableIdPop.hide();

            }
        });

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

        //System.out.println("Slot for Art - "+timeTableDAO.isSlotAvailable("1", "mon",
        //"Art","1"));
        // Classes 1
        // Subject all the subject go through
        // Teacher selected teacher
        
        int maxPeriodPerDay = 2;
        String timeTable = "1"; //Should be from the data in the textfield
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
                    int subjectMaxCountPerWeek = 0;

                    boolean isSubjectFound = false;

                    for (int subjectIndex = 0; subjectIndex < subjectList.size();
                            subjectIndex++) {
                        int currentCount ;

                        if (timeTableDAO.isSlotAvailable(timeTable, dayList.get(
                                dayIndex),
                                subjectList.get(subjectIndex), i + "")) {

                            subject = subjectList.get(subjectIndex);
                            subjectIndex = subjectList.size();

                            subjectMaxCountPerWeek = timeTableDAO.
                                    getCountPerWeek(subject, classList.get(
                                                    classIndex));                            
                            /* 
                             Calculation for determining the no of subjects 
                             to be added on this day. The max period for a day
                             would be two which will be retrived from a 
                             hardcoded vairiable maxPeriodPerDay                           
                             */
                            
                            currentCount = timeTableDAO.getCurrentInstances(subject,
                                            classList.get(classIndex));
                            System.out.println("Current Count - "+currentCount);
                            if((subjectMaxCountPerWeek-currentCount)>=maxPeriodPerDay){
                                //Insert two periods
                                
//                                boolean isEntryAvailable = timeTableDAO.isEntryAvailable(timeTable, dayList.get(
//                                dayIndex),
//                                subjectList.get(subjectIndex), i + "");
//                                
//                                boolean isSlotAvailable = timeTableDAO.isSlotAvailable(timeTable, dayList.get(
//                                dayIndex),
//                                subjectList.get(subjectIndex), i + "");
                                
                                 boolean isEntryAvailable = false;
                                 boolean isSlotAvailable = false;
                                
                                
                                if (isEntryAvailable && isSlotAvailable) {
                                    for (int j = 0; j < maxPeriodPerDay; j++) {
                                    timeTableDAO.updateTimeTableEntry(
                                            timeTable,
                                            classList.get(classIndex),
                                            dayList.get(dayIndex),
                                            classList.get(classIndex)+"A",
                                            i+"",
                                            subject);
                                    if (j==0) {
                                        i++;
                                    }
                                
                                }
                                }else{
                                for (int j = 0; j < maxPeriodPerDay; j++) {
                                    timeTableDAO.insertTimeTableEntry(
                                            timeTable,
                                            classList.get(classIndex),
                                            dayList.get(dayIndex),
                                            classList.get(classIndex)+"A",
                                            i+"",
                                            subject);
                                    if (j==0) {
                                        i++;
                                    }
                                
                                }
                                
                                    
                                }
//                                dayIndex++;
                                subjectIndex++;
                            }else if((subjectMaxCountPerWeek-currentCount)<maxPeriodPerDay &&
                                    (subjectMaxCountPerWeek-currentCount)>0){
                                //Insert one periods
//                                timeTableDAO.insertTimeTableEntry(
//                                            timeTable,
//                                            classList.get(classIndex),
//                                            dayList.get(dayIndex),
//                                            classList.get(classIndex)+"A",
//                                            i+"",
//                                            subject);
                            }
//                            
                            
                            
//                            System.out.println("no of periods found - "+timeTableDAO.getCurrentInstances(subject,
//                                            classList.get(classIndex)));
                            
                            
                        }

                    }

                    System.out.println("Class - " + classList.get(classIndex)
                            + " Subject - " + subject
                            + " Subject Max - " + subjectMaxCountPerWeek
                            + " Day - " + dayList.get(dayIndex)
                            + " Slot availability - " + i);
                    subject = "";

                }

            }
        }
        /*
    
         //<editor-fold defaultstate="collapsed" desc="Current Print Code">
         HashMap paramOne = new HashMap();
         paramOne.put("class_timetable_id", "1");
         paramOne.put("class_no", "1A");

         File file
         = new File(
         ".//Reports//timetable_1st_half.jasper");
         String imgOne = file.getAbsolutePath();
         ReportGenerator report = new ReportGenerator(imgOne, paramOne);

         report.setVisible(true);

         //----------------------
         HashMap param = new HashMap();
         param.put("class_teacher_timetable_id", "1");

         File fileOne
         = new File(
         ".//Reports//teacher_timetable.jasper");
         String img = fileOne.getAbsolutePath();
         ReportGenerator r = new ReportGenerator(img, param);

         r.setVisible(true);

         //            mb.ShowMessage(stage, ErrorMessages.SuccesfullyCreated,
         //                    MessageBoxTitle.INFORMATION.toString(),
         //                    MessageBox.MessageIcon.MSG_ICON_SUCCESS,
         //                    MessageBox.MessageType.MSG_OK);
         //</editor-fold>
         */
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

        timetableDataLoader(txtLabel.getText());
        timetableIdTable.setItems(timetableData);
        if (!timetableData.isEmpty()) {
            timetableIdPop.show(btnSearchCustomer);
        }

    }

    private void timetableDataLoader(String keyword) {

        timetableData.clear();
        ArrayList<ArrayList<String>> itemInfo
                = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> list = timeTableDAO.
                searchTimetableDetails(keyword);

        if (list != null) {

            for (int i = 0; i < list.size(); i++) {

                itemInfo.add(list.get(i));
            }

            if (itemInfo != null && itemInfo.size() > 0) {
                for (int i = 0; i < itemInfo.size(); i++) {

                    timetableIdPopup = new TimeTablePopup();
                    timetableIdPopup.colTimetableId.setValue(itemInfo.get(i).
                            get(0));
                    timetableIdPopup.colTitle.setValue(itemInfo.get(i).
                            get(1));

                    timetableData.add(timetableIdPopup);
                }
            }

        }

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

    private void loadSubjectToCombobox() {

        cmbSubject.setItems(null);
        ArrayList<String> classList = null;
        classList = timeTableDAO.loadSubject();
        if (classList != null) {
            try {
                ObservableList<String> List = FXCollections.observableArrayList(
                        classList);
                cmbSubject.setItems(List);
                cmbSubject.setValue(List.get(0));
            } catch (Exception e) {

            }

        }

    }

    private void loadGrnData() {

        TableTimetableData.clear();

        ArrayList<ArrayList<String>> additionalItemInfo
                = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> list = timeTableDAO.
                getTableInfo("1");

        if (list != null) {

            for (int i = 0; i < list.size(); i++) {

                additionalItemInfo.add(list.get(i));
            }

            if (additionalItemInfo != null && additionalItemInfo.size()
                    > 0) {
                for (int i = 0; i < additionalItemInfo.size(); i++) {

                    timeTable = new Timetable();

                    timeTable.colDay.
                            setValue(additionalItemInfo.get(i).get(0));
                    timeTable.colSlot.setValue(additionalItemInfo.get(
                            i).
                            get(1));
                    timeTable.colSubject.setValue(additionalItemInfo.get(i).
                            get(2));
                    timeTable.colTeacher.setValue(additionalItemInfo.
                            get(i).
                            get(3));

                    timetableData.add(timeTable);
                }
            }
        } else {
            mb.ShowMessage(stage, ErrorMessages.InvalidEvent, "Error",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);

        }

    }

    private void loadTeacherToCombobox() {

        cmbTeacher.setItems(null);
        ArrayList<String> classList = null;
        classList = timeTableDAO.loadTeacher();
        if (classList != null) {
            try {
                ObservableList<String> List = FXCollections.observableArrayList(
                        classList);
                cmbTeacher.setItems(List);
                cmbTeacher.setValue(List.get(0));
            } catch (Exception e) {

            }

        }
    }

    public class Timetable {

        public SimpleStringProperty colDay = new SimpleStringProperty(
                "tcDay");
        public SimpleStringProperty colSlot = new SimpleStringProperty(
                "tcSlot");
        public SimpleStringProperty colSubject
                = new SimpleStringProperty(
                        "tcSubject");
        public SimpleStringProperty colTeacher
                = new SimpleStringProperty(
                        "tcTeacher");

        public String getColDay() {
            return colDay.get();
        }

        public String getColSlot() {
            return colSlot.get();
        }

        public String getColSubject() {
            return colSubject.get();
        }

        public String getColTeacherIncharge() {
            return colTeacher.get();
        }

    }

}
