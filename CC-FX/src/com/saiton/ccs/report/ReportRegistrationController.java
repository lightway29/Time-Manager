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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
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
public class ReportRegistrationController extends AnchorPane implements
        Initializable, StagePassable {

    //<editor-fold defaultstate="collapsed" desc="initcomponents">
    @FXML
    private Button btnClose;

    @FXML
    private Button btnReportIdSearch;

    @FXML
    private Button btnSave;

    @FXML
    private TextField txtName;

    @FXML
    private Button btnDelete;

    @FXML
    private Insets x1;

    @FXML
    private TextField txtReportId;

    @FXML
    private ComboBox<String> cmbReportType;
//</editor-fold>

    private Stage stage;
    private PrinterReportFacade prdao;
    private boolean isUpdate = false;
    private final ValidationSupport vbase = new ValidationSupport();
    private final MessageBox mb = SimpleMessageBoxFactory.createMessageBox();
    private PopOver reportPop;
    private TableView<Report> tvReports;
    private ObservableList<Report> reportsData;
    private ObservableList<String> typeData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void setUpdateMode() {
        isUpdate = true;
        btnDelete.setVisible(true);
    }

    private void setRegisterMode() {
        isUpdate = false;
        btnDelete.setVisible(false);
    }

    public void clearInput() {
        txtName.setText("");
        txtReportId.setText(prdao.generateReportId());
        cmbReportType.getSelectionModel().selectFirst();
        setRegisterMode();

    }

    private void createPop() {
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
                        setUpdateMode();
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

    public void validate() {
        vbase.registerValidator(txtName, Validator.createEmptyValidator(
                ErrorMessages.Empty));
        vbase.registerValidator(txtReportId, Validator.
                createEmptyValidator(ErrorMessages.Empty));
        vbase.registerValidator(cmbReportType, Validator.createEmptyValidator(
                ErrorMessages.Empty));
    }

    @Override
    public void setStage(Stage stage, Object[] obj) {
        this.stage = stage;
        prdao = new PrinterReportFacade();
        createPop();
        stage.setOnCloseRequest(event -> {
            if (reportPop.isShowing()) {
                event.consume();
                reportPop.hide();
            }
        });
        typeData = cmbReportType.getItems();
        typeData.clear();
        List<String> types = prdao.selectAllReportTypes();
        if (types != null) {
            typeData.addAll(types);
        }
        clearInput();
        validate();
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
    void btnSaveOnAction(ActionEvent event) {
        if (vbase.isInvalid()) {
            mb.ShowMessage(stage, "Please enter valid data.",
                    "Report Registration");
            return;
        }

        boolean b;
        if (isUpdate) {
            b = prdao.updateReport(txtReportId.getText(), txtName.getText(),
                    cmbReportType.getValue());

        } else {
            b = prdao.insertReport(txtName.getText(), cmbReportType.getValue());
        }

        if (b) {
            mb.ShowMessage(stage, "Success",
                    "Report Registration");
        } else {
            mb.ShowMessage(stage, "Failed",
                    "Report Registration");
        }
        clearInput();
    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {
        if (reportPop.isShowing()) {
            reportPop.hide();
        } else {
            stage.close();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (!isUpdate) {
            return;
        }

        if (vbase.isInvalid()) {
            mb.ShowMessage(stage, "Please enter valid data.",
                    "Report Registration");
            return;
        }

        if (prdao.deleteReport(txtReportId.getText())) {
            mb.ShowMessage(stage, "Success",
                    "Report Registration");
        } else {
            mb.ShowMessage(stage, "Failed",
                    "Report Registration");
        }

        clearInput();
    }

    @FXML
    void cmbReportTypeOnAction(ActionEvent event) {
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

}
