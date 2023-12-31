package app.controllers;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import app.models.Patient;
import app.views.AlertBoxView;
import database.DBConnection;
import json.JSONFile;

// Javafx lib
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class EditController implements Initializable {
    // House field fxid(s)
    @FXML
    private TextField name_field;
    @FXML
    private TextField capacity_field;

    // Database field fxid(s)
    @FXML
    private TextField db_server_url_field;
    @FXML
    private TextField db_username_field;
    @FXML
    private TextField db_password_field;

    private Map<String, Object> _map = JSONFile.toMap();

    private void _setOldVal(TextField field, String oldVal) {
        field.setText(oldVal);
        field.setPromptText(oldVal);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set initial field text and placeholder according to json data
        _setOldVal(name_field, (String) _map.get("house_name"));
        _setOldVal(capacity_field, Long.toString((Long) _map.get("house_capacity")));
        _setOldVal(db_server_url_field, (String) _map.get("db_srv_url"));
        _setOldVal(db_username_field, (String) _map.get("db_usr"));
        _setOldVal(db_password_field, (String) _map.get("db_pw"));

        // Listen to capacity_field on value change
        capacity_field.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldVal, String newVal) {
                // Remove non-number character
                if (!newVal.matches("\\d*")) {
                    capacity_field.setText(newVal.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    private void _showConfigPage() throws Exception {
        // Load fxml file
        Parent configPage = FXMLLoader.load(getClass().getResource("../views/configSubView.fxml"));

        HomeController.setCenterPage(configPage);
    }

    @FXML
    private void _cancelBtnHandler(ActionEvent event) throws Exception {
        _showConfigPage();
    }

    @FXML
    private void _saveBtnHandler(ActionEvent event) throws Exception  {
        // Get all input field value
        String houseName = name_field.getText().trim()
            .replaceAll("\\s{2,}", " ");
        int houseCapacity = 0;

        try {
            houseCapacity = Integer.parseInt(capacity_field.getText());
        }
        catch(Exception e) {} // Ignore parsing error

        String dbServerURL = db_server_url_field.getText()
            .replace("(http:|https:)", "")
            .replaceAll("/", "")
            .replaceAll("\\s{1,}", "");
        String dbUsername = db_username_field.getText();
        String dbPassword = db_password_field.getText();

        if (!houseName.isEmpty() && houseCapacity != 0 && 
            !dbServerURL.isEmpty() && !dbUsername.isEmpty()) {
            // Check if new house_capacity is valid (not less than current total patient)
            if (DBConnection.isEstablished() && houseCapacity < Patient.fetch().size()) {
                AlertBoxView.showAlert(AlertType.ERROR, "Capacity Exceeded", "House capacity should not less than patient total");
                return;
            }

            // Check if database url is not valid
            if (!dbServerURL.matches("^[a-zA-Z0-9]{1,}(:[0-9]{1,5})?$")) {
                AlertBoxView.showAlert(AlertType.ERROR, "URL Error", "Database URL format is not valid");
                return;
            }

            // Replace map old value to new value
            _map.put("house_name", houseName);
            _map.put("house_capacity", houseCapacity);
            _map.put("db_srv_url", dbServerURL);
            _map.put("db_usr", dbUsername);
            _map.put("db_pw", dbPassword);

            // Write json file with new map value
            JSONFile.write(_map);

            _showConfigPage();

            DBConnection.init(); // Initialize database

            // Check connection status
            if (!DBConnection.isEstablished()) {
                AlertBoxView.showAlert(AlertType.WARNING, "Connection Error", 
                "Couldn't communicate with database server. Make sure your credentials are correct and server is online");
            }
        }
    }
}
