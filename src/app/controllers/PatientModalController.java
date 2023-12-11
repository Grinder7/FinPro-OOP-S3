package app.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PatientModalController implements Initializable {
    @FXML
    private Label title_label;

    // Input field fxid(s)
    @FXML
    private TextField name_field;
    @FXML
    private TextField age_field;
    @FXML
    private ComboBox<String> dropdown;
    @FXML
    private TextArea disability_det_field;
    
    @Override
    public void initialize(URL location, ResourceBundle resoruce) {

    }

    @FXML
    private void _saveBtnHandler(ActionEvent event) {

    }

}
