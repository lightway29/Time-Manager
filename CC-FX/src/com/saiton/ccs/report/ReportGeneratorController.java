/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.report;

import com.saiton.ccs.base.UserPermission;
import com.saiton.ccs.base.UserSession;
import com.saiton.ccs.common.TimeConvert;
import com.saiton.ccs.msgbox.MessageBox;
import com.saiton.ccs.msgbox.SimpleMessageBoxFactory;
import com.saiton.ccs.popup.ReportPopup;
//import com.saiton.ccs.printerservice.PrinterRegistry;
import com.saiton.ccs.reportdao.ReportDAO;
import com.saiton.ccs.ui.ReportGenerator;
import com.saiton.ccs.ui.StagePassable;
import com.saiton.ccs.ui.UiMode;
import com.saiton.ccs.validations.CustomDatePickerValidationImpl;
import com.saiton.ccs.validations.CustomTextFieldValidationImpl;

import com.saiton.ccs.validations.ErrorMessages;
import com.saiton.ccs.validations.FormatAndValidate;
import com.saiton.ccs.validations.MessageBoxTitle;
import com.saiton.ccs.validations.Validatable;
import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.control.PopOver;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;

/**
 * FXML Controller class
 *
 * @author lakshan
 */
public class ReportGeneratorController extends AnchorPane implements
        Initializable, Validatable, StagePassable {

    //<editor-fold defaultstate="collapsed" desc="initcomponents">
    ReportDAO reportDAO = new ReportDAO();
    private final ValidationSupport validationSupport = new ValidationSupport();
    private final FormatAndValidate fav = new FormatAndValidate();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//    private PrinterRegistry printerRegistry = new PrinterRegistry();

    //report popup
    private TableView reportTable = new TableView();
    private PopOver reportPop;
    private ObservableList<ReportPopup> reportData = FXCollections.
            observableArrayList();
    private ReportPopup reportPopup = new ReportPopup();

    private Stage stage;
    private MessageBox mb;
    boolean isReportSelected = false;
    String url;

    private String userId;
    private String userName;
    private String userCategory;
    private boolean insert = false;
    private boolean update = false;
    private boolean delete = false;
    private boolean view = false;

    ObservableList<String> HoursList = FXCollections.observableArrayList(
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
            "12"
    );

    ObservableList<String> MinuitsList = FXCollections.observableArrayList(
            "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
            "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
            "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
            "51", "52", "53", "54", "55", "56", "57", "58", "59"
    );

    ObservableList<String> AMPM = FXCollections.observableArrayList(
            "AM", "PM"
    );

    TimeConvert timeConvert = new TimeConvert();
    Date currentDate = new Date();
    DateFormat time = new SimpleDateFormat("HH:mm");
    Calendar cal = Calendar.getInstance();
    @FXML
    private DatePicker dtFromDate;
    @FXML
    private DatePicker dtToDate;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mb = SimpleMessageBoxFactory.createMessageBox();

        dateFormatterToDate("yyyy-MM-dd");
        dateFormatterFromDate("yyyy-MM-dd");
        dtFromDate.setValue(LocalDate.now());
        dtToDate.setValue(LocalDate.now());

    }

    private void txtReportNameOnKeyReleased(KeyEvent event) {

    }

    private void btnSearchReportOnAction(ActionEvent event) {

    }

    private void loadReports(String keyword) {
        reportData.clear();
        ArrayList<ArrayList<String>> reportInfo
                = new ArrayList<ArrayList<String>>();

        ArrayList<ArrayList<String>> list;

        if (delete == true) {
            list = reportDAO.loadeReports(keyword, "");

            if (list != null) {

                for (int i = 0; i < list.size(); i++) {

                    reportInfo.add(list.get(i));
                }

                if (reportInfo != null && reportInfo.size() > 0) {
                    for (int i = 0; i < reportInfo.size(); i++) {

                        reportPopup = new ReportPopup();

                        reportPopup.colReportID.setValue(reportInfo.get(i).
                                get(0));
                        reportPopup.colReportName.setValue(reportInfo.get(i).
                                get(1));

                        reportData.add(reportPopup);
                    }
                }

            }
        } else {

            list = reportDAO.loadeReports(keyword,
                    " AND is_delete_privilege = '0' ");

            if (list != null) {

                for (int i = 0; i < list.size(); i++) {

                    reportInfo.add(list.get(i));
                }

                if (reportInfo != null && reportInfo.size() > 0) {
                    for (int i = 0; i < reportInfo.size(); i++) {

                        reportPopup = new ReportPopup();

                        reportPopup.colReportID.setValue(reportInfo.get(i).
                                get(0));
                        reportPopup.colReportName.setValue(reportInfo.get(i).
                                get(1));

                        reportData.add(reportPopup);
                    }
                }

            }

        }

    }

    private void gerReportURL() {

    }

    @FXML
    private void dtFromDateOnAction(ActionEvent event) {
        validatorInitialization();
    }

    @FXML
    private void dtToDateOnAction(ActionEvent event) {
        validatorInitialization();
    }

    public String getTime(String hours, String minuits, String time) {
        return String.format("%s:%s%s", hours, minuits, time);
    }

    @FXML
    private void btnSaveOnAction(ActionEvent event) {

        //=====================Print out===============================
        HashMap param = new HashMap();
        param.put("fromDate", dtFromDate.getValue().toString());
        param.put("toDate", dtToDate.getValue().toString());

        File fil = new File(".//Reports//Profit&Loss.jasper");
        String img = fil.getAbsolutePath();
        com.saiton.ccs.uihandle.ReportGenerator r
                = new com.saiton.ccs.uihandle.ReportGenerator(img, param);
        r.setVisible(true);
        //=============================================================

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

        dtFromDate.setValue(LocalDate.now());
        dtToDate.setValue(LocalDate.now());
        dateFormatterToDate("yyyy-MM-dd");
        dateFormatterFromDate("yyyy-MM-dd");
        dtFromDate.setValue(LocalDate.now());
        dtToDate.setValue(LocalDate.now());
        validatorInitialization();
    }

    @Override
    public void clearValidations() {

    }

    @Override
    public void setStage(Stage stage, Object[] obj) {

        this.stage = stage;
        setUserAccessLevel();

        reportTable = reportPopup.tableViewLoader(reportData);

        reportTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                try {
                    ReportPopup p = (ReportPopup) reportTable.
                            getSelectionModel().getSelectedItem();

                } catch (NullPointerException n) {

                }
                reportPop.hide();
                validatorInitialization();
            }

        });

        reportTable.setOnMousePressed(e -> {

            if (e.getButton() == MouseButton.SECONDARY) {

                reportPop.hide();
                validatorInitialization();

            }

        });

        reportPop = new PopOver(reportTable);

        stage.setOnCloseRequest(e -> {

            if (reportPop.isShowing()) {

                e.consume();
                reportPop.hide();

            }
        });

        validatorInitialization();

    }

    private void setUiMode(UiMode uiMode) {

        switch (uiMode) {

            case SAVE:
                disableUi(false);
                break;

            case DELETE:
                disableUi(true);

                break;

            case READ_ONLY:
                disableUi(false);
                btnSave.setDisable(true);
                btnSave.setVisible(false);

                break;

            case ALL_BUT_DELETE:
                disableUi(false);
                break;

            case FULL_ACCESS:
                disableUi(false);

                break;

            case NO_ACCESS:
                disableUi(true);

                break;

        }

    }

    private void disableUi(boolean state) {
        btnSave.setDisable(state);
        btnSave.setVisible(!state);

        btnCancel.setDisable(state);
        btnCancel.setVisible(!state);

        dtFromDate.setDisable(state);
        dtFromDate.setVisible(!state);

        dtToDate.setDisable(state);
        dtToDate.setVisible(!state);

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
                System.out.println("Reached");
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

    private void validatorInitialization() {

        try {

            validationSupport.registerValidator(dtFromDate,
                    new CustomDatePickerValidationImpl(dtFromDate,
                            !fav.doesDate1GraterThanDate2(dateFormat.parse(
                                            dtFromDate.getValue().toString()),
                                    dateFormat.parse(dtToDate.getValue().
                                            toString())),
                            ErrorMessages.FromDateOverLap));

            validationSupport.registerValidator(dtToDate,
                    new CustomDatePickerValidationImpl(dtToDate,
                            !fav.doesDate1GraterThanDate2(dateFormat.parse(
                                            dtFromDate.getValue().toString()),
                                    dateFormat.parse(dtToDate.getValue().
                                            toString())),
                            ErrorMessages.ToDateOverLap));

        } catch (NullPointerException | ParseException e) {

        }
    }

    private void dateFormatterFromDate(String pattern) {

        dtFromDate.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(
                    pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

    }

    private void dateFormatterToDate(String pattern) {

        dtToDate.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(
                    pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

    }

//    private String getAbbriviation(String time) {
//        String abriviation;
//        String Hours24 = time.split(":")[0];
//
//        if (Integer.parseInt(Hours24.trim()) < 12) {
//            abriviation = "AM";
//        } else {
//            abriviation = "PM";
//        }
//
//        String timeIn12 = timeConvert.convertTo12HoursFormat(time);
//        return timeIn12 + abriviation;
//    }
//
//    private void getTime(String time) {
//        String abriviation;
//        String Hours24 = time.split(":")[0];
//
//        if (Integer.parseInt(Hours24.trim()) < 12) {
//            abriviation = "AM";
//        } else {
//            abriviation = "PM";
//        }
//
//        String timeIn12 = timeConvert.convertTo12HoursFormat(time);
//
//        String Hours = timeIn12.split(":")[0];
//        String Hours1 = timeIn12.split(":")[1];
//        String Minits = Hours1.split(" ")[0];
//        txtTime.setText(timeIn12 + abriviation);
//    }
}
