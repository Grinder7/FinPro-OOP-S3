package app.controllers;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

// Model(s)
import app.models.Patient;

import app.json.JSONFile;

import app.views.AlertBoxView;

// Javafx lib(s)
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

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

    private Stage _modalStage;
    private String _action;
    private int _idx;

    public void setStage(Stage stage) {_modalStage = stage;}

    public void setAction(String action) {_action = action;}

    public void setIdx(int idx) {_idx = idx;}

    private void _setOldVal(TextField field, String val) {
        field.setText(val);
        field.setPromptText(val);
    }

    private void _setOldVal(TextArea field, String val) {
        field.setText(val);
        field.setPromptText(val);
    }

    @Override
    public void initialize(URL location, ResourceBundle resource) {
        // Intialize label and button text
        Platform.runLater(() -> {
            if (_action.equals("insert")) {
                title_label.setText("Add New Patient");
            } 
            else {
                title_label.setText("Update Patient");

                Patient obj = PatientListController.getList().get(_idx);

                // Initialize field value(s)
                _setOldVal(name_field, obj.getName());
                _setOldVal(age_field, Integer.toString(obj.getAge()));
                _setOldVal(disability_det_field, obj.getDisabilityDetail());

                dropdown.getSelectionModel().select((obj.getGender().equals("M") ? 0 : 1));

                obj = null;

                System.gc();
            }
        });

        // Initialize dropdown menu(s)
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Male");
        list.add("Female");

        dropdown.getItems().clear();
        dropdown.setItems(list);

        // Listen to name_field on value change
        name_field.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldVal, String newVal) {
                if (!newVal.matches("[a-zA-Z ,.']*")) {
                    name_field.setText(newVal.replaceAll("[^[a-zA-Z ,.']]", ""));
                }
                // Validate string length
                else if (newVal.length() > 255) {
                    name_field.setText(newVal.substring(0, 255));
                }
            }
        });

        // Listen to age_field on value change
        age_field.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldVal, String newVal) {
                // Remove non-number character
                if (!newVal.matches("\\d*")) {
                    age_field.setText(newVal.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    @FXML
    private void _saveBtnHandler(ActionEvent event) {
        // Get all field value(s)
        String name = name_field.getText().trim()
                .replaceAll("\\s{2,}", " ");
        int age = 0;

        try {
            age = Integer.parseInt(age_field.getText());
        } 
        catch (Exception e) {} // Ignore parsing error

        String gender = ""; 
        
        gender = Objects.toString(dropdown.getValue()
            .substring(0, 1), "");

        String disabilityDet = disability_det_field.getText().trim()
            .replaceAll("\\s{2,}", " ");

        if (!name.isEmpty() && age != 0 && !gender.isEmpty() && !disabilityDet.isEmpty()) {
            // Insert action
            if (_action.equals("insert")) {
                if (Patient.fetch().size() + 1 <= JSONFile.toMap().get("house_capacity")) {
                    new Patient(name, age, gender, disabilityDet).insert();
                }
                else {
                    AlertBoxView(AlertType.WARNING, "Capacity Exceeded", "You cannot add more patient, due to your max capacity");
                }
            }
            // Update action
            else {
                Patient obj = PatientListController.getList().get(_idx);

                obj.update(new Patient(name, age, gender, disabilityDet));
            }

            _modalStage.close();
        }
    }
}
