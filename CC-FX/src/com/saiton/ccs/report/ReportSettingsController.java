package com.saiton.ccs.report;

import com.saiton.ccs.msgbox.MessageBox;
import com.saiton.ccs.msgbox.SimpleMessageBoxFactory;
import com.saiton.ccs.uihandle.StagePassable;
import com.saiton.ccs.validations.ErrorMessages;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

/**
 * FXML Controller class
 *
 * @author Saitonya
 */
public class ReportSettingsController extends AnchorPane implements
        Initializable, StagePassable {

    //<editor-fold defaultstate="collapsed" desc="initcomponents">
    @FXML
    private Button btnClose;

    @FXML
    private TextField txtName;

    @FXML
    private ComboBox<String> cmbPrinterType;

    @FXML
    private ComboBox<String> cmbReportType;

    @FXML
    private Label lblItemId1;

    @FXML
    private TextArea txtAreaDescription;

    @FXML
    private TextField txtReportId;

    @FXML
    private Label lblItemId;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnReportIdSearch;

    @FXML
    private Button btnPrinterIdSearch;

    @FXML
    private Button btnAssign;

    @FXML
    private Insets x1;

    @FXML
    private Insets x11;

    @FXML
    private Insets x2;

    @FXML
    private TextField txtPrinterId;

    @FXML
    private TextField txtTitle;

//</editor-fold>
    //-----------------------------------------------------------------
    @FXML
    private TableView<PrinterReport> tblReportList;

    @FXML
    private TableColumn<PrinterReport, String> tcRid;

    @FXML
    private TableColumn<PrinterReport, String> tcRname;

    @FXML
    private TableColumn<PrinterReport, String> tcRtype;

    @FXML
    private TableColumn<PrinterReport, String> tcPid;

    @FXML
    private TableColumn<PrinterReport, String> tcPname;

    @FXML
    private TableColumn<PrinterReport, String> tcPtype;
    //----------------------------------------------------------------------

    private Stage stage;
    private PrinterReportFacade prdao;
    private final ValidationSupport vbase = new ValidationSupport();
    private final MessageBox mb = SimpleMessageBoxFactory.createMessageBox();

    private PopOver reportPop;
    private TableView<Report> tvReports;
    private ObservableList<Report> reportsData;
    private ObservableList<String> reportTypeData;

    private PopOver printerPop;
    private TableView<Printer> tvPrinters;
    private ObservableList<Printer> printersData;
    private ObservableList<String> printerTypeData;

    private ObservableList<PrinterReport> printerReportData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void clearInput() {
        clear();
        loadPrinterReports();
    }

    private void clear() {
        txtAreaDescription.setText("");
        txtName.setText("");
        txtPrinterId.setText("");
        txtReportId.setText("");
        txtTitle.setText("");
        cmbPrinterType.getSelectionModel().selectFirst();
        cmbReportType.getSelectionModel().selectFirst();
    }

    private void createPop() {
        createPopPrinter();
        createPopReport();
    }

    private void createPopReport() {
        tvReports = new TableView<>();
        TableColumn<Report, String> pid = new TableColumn<>("Report ID");
        pid.setMinWidth(50.0);
        TableColumn<Report, String> title = new TableColumn<>("Title");
        title.setMinWidth(50.0);
        TableColumn<Report, String> type = new TableColumn<>("Type");
        type.setMinWidth(50.0);

        pid.setCellValueFactory(
                new PropertyValueFactory("rid"));

        title.setCellValueFactory(
                new PropertyValueFactory("name"));

        type.setCellValueFactory(
                new PropertyValueFactory("type"));

        tvReports.getColumns().addAll(pid, title, type);

        reportsData = tvReports.getItems();
        reportsData.clear();
        reportPop = new PopOver(tvReports);

        tvReports.setOnMouseClicked(e -> {

            if (e.getClickCount() == 1 && (e.getButton()
                    == MouseButton.SECONDARY)) {
                reportPop.hide();

            } else if (e.getClickCount() == 2) {
                try {

                    Report report = tvReports.getSelectionModel().
                            getSelectedItem();

                    if (report != null) {
                        txtReportId.setText(report.getRid());
                        txtName.setText(report.getName());
                        cmbReportType.getSelectionModel().select(report.
                                getType());
                    }

                } catch (Exception n) {

                }

                reportPop.hide();
                validate();
            }
        });

    }

    private void createPopPrinter() {
        tvPrinters = new TableView<>();
        TableColumn<Printer, String> pid = new TableColumn<>("Printer ID");
        pid.setMinWidth(50.0);
        TableColumn<Printer, String> title = new TableColumn<>("Title");
        title.setMinWidth(50.0);
        TableColumn<Printer, String> type = new TableColumn<>("Type");
        type.setMinWidth(50.0);

        pid.setCellValueFactory(
                new PropertyValueFactory("pid"));

        title.setCellValueFactory(
                new PropertyValueFactory("name"));

        type.setCellValueFactory(
                new PropertyValueFactory("type"));

        tvPrinters.getColumns().addAll(pid, title, type);

        printersData = tvPrinters.getItems();
        printersData.clear();
        printerPop = new PopOver(tvPrinters);

        tvPrinters.setOnMouseClicked(e -> {

            if (e.getClickCount() == 1 && (e.getButton()
                    == MouseButton.SECONDARY)) {
                printerPop.hide();

            } else if (e.getClickCount() == 2) {
                try {
                    Printer printer = tvPrinters.getSelectionModel().
                            getSelectedItem();

                    if (printer != null) {
                        txtPrinterId.setText(printer.getPid());
                        txtTitle.setText(printer.getName());
                        txtAreaDescription.setText(printer.getDescription());
                        cmbPrinterType.getSelectionModel().select(printer.
                                getType());
                    }

                } catch (Exception n) {

                }

                printerPop.hide();
                validate();
            }
        });
    }

    private void loadPrinterReports() {
        printerReportData.clear();
        List<PrinterReport> prs = prdao.selectAllPrinterReports();
        if (prs != null) {
            printerReportData.addAll(prs);
        }
    }

    public void validate() {
        vbase.registerValidator(txtPrinterId, Validator.createEmptyValidator(
                "Need to select a printer"));
        vbase.registerValidator(txtReportId, Validator.
                createEmptyValidator("Need to select a report"));
    }

    @Override
    public void setStage(Stage stage, Object[] obj) {
        this.stage = stage;
        prdao = new PrinterReportFacade();
        createPop();
        stage.setOnCloseRequest(event -> {
            if (printerPop.isShowing()) {
                event.consume();
                printerPop.hide();
            }
            if (reportPop.isShowing()) {
                event.consume();
                reportPop.hide();
            }
        });
        printerTypeData = cmbPrinterType.getItems();
        printerTypeData.clear();
        List<String> types = prdao.selectAllPrinterTypes();
        if (types != null) {
            printerTypeData.addAll(types);
        }
        reportTypeData = cmbReportType.getItems();
        reportTypeData.clear();
        List<String> reporttypes = prdao.selectAllReportTypes();
        if (reporttypes != null) {
            reportTypeData.addAll(reporttypes);
        }

        tcPid.setCellValueFactory(
                new PropertyValueFactory("pid"));

        tcPname.setCellValueFactory(
                new PropertyValueFactory("pname"));

        tcPtype.setCellValueFactory(
                new PropertyValueFactory("ptype"));

        tcRid.setCellValueFactory(
                new PropertyValueFactory("rid"));

        tcRname.setCellValueFactory(
                new PropertyValueFactory("rname"));

        tcRtype.setCellValueFactory(
                new PropertyValueFactory("rtype"));
        printerReportData = tblReportList.getItems();

        clearInput();
        validate();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if (printerReportData.size() == 0) {
            if (mb.ShowMessage(stage,
                    "You have not assigned a single printer/report.\n"
                    + "This will clear all prior printer/report assignment.\n"
                    + "Are you sure you want to continue ?", "Printer Settings",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK)
                    == MessageBox.MessageOutput.MSG_NO) {
                return;
            }
        }

        if (prdao.setAllPrinterReports(printerReportData)) {
            mb.ShowMessage(stage, "Success",
                    "Printer Settings");
        } else {
            mb.ShowMessage(stage, "Failed",
                    "Printer Settings");
        }

        clearInput();
    }

    @FXML
    void btnPrinterIdSearchOnAction(ActionEvent event) {
        printersData.clear();

        List<Printer> prints = prdao.selectAllPrinters();

        printersData.addAll(prints);
        if (!printerPop.isShowing()) {
            printerPop.show(btnPrinterIdSearch);
        }
    }

    @FXML
    void btnReportIdSearchOnAction(ActionEvent event) {
        reportsData.clear();

        List<Report> reports = prdao.selectAllReports();

        reportsData.addAll(reports);
        if (!reportPop.isShowing()) {
            reportPop.show(btnReportIdSearch);
        }
    }

    @FXML
    void btnAssignOnAction(ActionEvent event) {
        if (vbase.isInvalid()) {
            mb.ShowMessage(stage, "Select a printer and a report",
                    "Report Settings",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }
        String pid = txtPrinterId.getText();
        String rid = txtReportId.getText();

        for (PrinterReport pr : printerReportData) {
            if (pr.getPid().equals(pid) && pr.getRid().equals(rid)) {
                mb.ShowMessage(stage, "Already assigned",
                        "Report Settings",
                        MessageBox.MessageIcon.MSG_ICON_FAIL,
                        MessageBox.MessageType.MSG_OK);
                return;
            }
        }

        PrinterReport printerReport = new PrinterReport();
        printerReport.setFromStringArray(new String[]{
            txtPrinterId.getText(),
            txtTitle.getText(),
            cmbPrinterType.getValue(),
            txtAreaDescription.getText(),
            txtReportId.getText(),
            txtName.getText(),
            cmbReportType.getValue()
        });

        printerReportData.add(printerReport);

        clear();
    }

    @FXML
    void btnRemoveOnAction(ActionEvent event) {
        PrinterReport pr = tblReportList.getSelectionModel().getSelectedItem();
        if (pr == null) {
            mb.ShowMessage(stage, "Select a row to remove it",
                    "Report Settings",
                    MessageBox.MessageIcon.MSG_ICON_FAIL,
                    MessageBox.MessageType.MSG_OK);
            return;
        }

        printerReportData.remove(pr);
    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {
        if (printerPop.isShowing()) {
            printerPop.hide();
            return;
        }
        if (reportPop.isShowing()) {
            reportPop.hide();
            return;
        }

        stage.close();
    }

    @FXML
    void cmbPrinterTypeOnAction(ActionEvent event) {
        validate();
    }

    @FXML
    void cmbReportTypeOnAction(ActionEvent event) {
        validate();
    }

    @FXML
    void txtAreaDescriptionOnKeyReleased(KeyEvent event) {
        validate();
    }

    @FXML
    void txtNameOnkeyReleased(KeyEvent event) {
        validate();
    }

    @FXML
    void txtReportIdOnKeyReleased(KeyEvent event) {
        validate();
    }

    @FXML
    void txtPrinterIdOnKeyReleased(KeyEvent event) {
        validate();
    }

    @FXML
    void txtTitleOnKeyReleased(KeyEvent event) {
        validate();
    }

    @FXML
    void tblReportListOnMouseClicked(MouseEvent event) {
        //not neccesory
    }

}
