package app.controllers;

import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL location, ResourceBundle recourses) {
        // Initialize dropdown menus
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Male");
        list.add("Female");

        dropdown.getItems().clear();
        dropdown.setItems(list);

        // Listen to phone_num_field on value change
        phone_num_field.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldVal, String newVal) {
                // Remove non-number character
                if (!newVal.matches("\\d*")) {
                    phone_num_field.setText(newVal.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    @FXML
    private Button save_btn;

    @FXML
    void _saveBtnHandler(ActionEvent event) {
    }
}
