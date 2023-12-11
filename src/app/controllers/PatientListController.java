package app.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import app.models.Patient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class PatientListController implements Initializable {
    // Layout fxid(s)
    @FXML
    private TableView<Patient> table;

    // Column fxid(s)
    @FXML
    private TableColumn<Integer, Void> num_col;
    @FXML
    private TableColumn<Patient, String> name_col;
    @FXML
    private TableColumn<Patient, Integer> age_col;
    @FXML
    private TableColumn<Patient, String> gender_col;
    @FXML
    private TableColumn<Patient, String> disability_det_col;
    @FXML
    private TableColumn<HBox, Void> actions_col;

    // Input field fxid(s)
    @FXML
    private TextField search_field;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @FXML
    private void _addBtnHandler(MouseEvent event) {

    }

    @FXML
    private void _searchBtnHandler(MouseEvent event) {

    }

}
