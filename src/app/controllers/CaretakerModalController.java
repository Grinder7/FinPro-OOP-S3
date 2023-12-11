package app.controllers;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import app.models.Caretaker;

// Javafx lib(s)
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    private int _idx;

    public void setStage(Stage stage) {_modalStage = stage;}

    public void setAction(String action) {_action = action;}

    public void setIdx(int idx) {_idx = idx;}

    private void _setOldVal(TextField field, String val) {
        field.setText(val);
        field.setPromptText(val);
    }

    @Override
    public void initialize(URL location, ResourceBundle recourses) {
        // Intialize label and button text
        Platform.runLater(() -> {
            if (_action.equals("insert")) {
                title_label.setText("Add New Caretaker");
            }
            else {
                title_label.setText("Update Caretaker");

                Caretaker obj = CaretakerListController.getList().get(_idx);

                // Initialize field value(s)
                _setOldVal(name_field, obj.getName());
                _setOldVal(phone_num_field, obj.getPhoneNum());
                _setOldVal(age_field, Integer.toString(obj.getAge()));
                
                dropdown.getSelectionModel().select((
                    obj.getGender().equals("M") ? 0 : 1
                ));

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

        // Listen to phone_num_field on value change
        name_field.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldVal, String newVal) {
                // Remove non-number character
                if (!newVal.matches("[a-zA-Z ,.']*")) {
                    name_field.setText(newVal.replaceAll("[^[a-zA-Z ,.']]", ""));
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
        // Get all field value(s)
        String name = name_field.getText().trim()
            .replaceAll("\\s{2,}", " ");
        String phoneNum = phone_num_field.getText();
        int age = 0;

        try {
            age = Integer.parseInt(age_field.getText());
        }
        catch (Exception e) {} // Ignore parsing error

        String gender = Objects.toString(dropdown.getValue()
            .substring(0, 1), "");

        if (!name.isEmpty() && !phoneNum.isEmpty() && age != 0 && !gender.isEmpty()) {
            // Insert action
            if (_action.equals("insert")) {
                Caretaker obj = new Caretaker(name, phoneNum, age, gender);

                obj.insert();

                // Add new caretaker into table list
                CaretakerListController.getList().add(obj);
            }
            // Update action
            else {
                Caretaker newObj = new Caretaker(name, phoneNum, age, gender);

                Caretaker obj = CaretakerListController.getList().get(_idx);

                obj.setName(newObj.getName());
                obj.setPhoneNum(newObj.getPhoneNum());
                obj.setAge(newObj.getAge());
                obj.setGender(newObj.getGender());

                newObj = null;

                System.gc();
            }

            _modalStage.close();
        }
    }
}
