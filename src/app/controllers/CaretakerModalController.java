package app.controllers;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import app.models.Caretaker;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
// Javafx lib(s)
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CaretakerModalController implements Initializable {
    @FXML
    private Label title_label;

    // Field fxid(s)
    @FXML
    private TextField name_field;
    @FXML
    private TextField phone_num_field;
    @FXML
    private TextField age_field;
    @FXML
    private ComboBox<String> dropdown;

    @FXML
    private Button save_btn;

    private Stage _modalStage;

    private String _action;

    public void setStage(Stage stage) {_modalStage = stage;}
    public void setAction(String action) {_action = action;}

    @Override
    public void initialize(URL location, ResourceBundle recourses) {
        // Intialize label and button text
        Platform.runLater(() -> {
            if (_action.equals("insert")) {
                title_label.setText("Add New Caretaker");
            }
            else {
                title_label.setText("Update Caretaker");
            }
        });

        // Initialize dropdown menu(s)
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Male");
        list.add("Female");

        dropdown.getItems().clear();
        dropdown.setItems(list);

        // Listen to phone_num_field on value change
        name_field.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldVal, String newVal) {
                // Remove non-number character
                if (!newVal.matches("\\[a-zA-Z ,.']")) {
                    name_field.setText(newVal.replaceAll("[^\\[a-zA-Z ,.']]", ""));
                }
            }
        });

        // Listen to phone_num_field on value change
        phone_num_field.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldVal, String newVal) {
                // Remove non-number character
                if (!newVal.matches("\\d*")) {
                    phone_num_field.setText(newVal.replaceAll("[^\\d]", ""));
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
        String name = name_field.getText().trim().replaceAll("\\s{2,}", " ");
        String phoneNum = phone_num_field.getText();
        int age = 0;

        try {
            age = Integer.parseInt(age_field.getText());
        }
        catch (Exception e) {} // Ignore parsing error

        String gender = Objects.toString(dropdown.getValue()
            .substring(0, 1), "");

        if (!name.isEmpty() && !phoneNum.isEmpty() && age != 0 && !gender.isEmpty()) {
            if (_action.equals("insert")) {Caretaker.insert(name, phoneNum, age, gender);}
            else {}

            _modalStage.close();
        }
    }
}
