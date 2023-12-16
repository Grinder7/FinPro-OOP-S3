package app.controllers;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import app.Main;

import app.views.AlertBoxView;

import json.JSONFile;

// Javafx lib
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class Intro2Controller implements Initializable {
    // Input field fxid(s)
    @FXML
    private TextField capacity_field;
    @FXML
    private TextField db_server_url_field;
    @FXML
    private TextField db_username_field;
    @FXML
    private TextField db_password_field;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    @FXML
    private void _backBtnHandler() throws Exception {
        Main.showPage("./views/intro1View.fxml", false);
    }

    @FXML
    private void _nextBtnHandler() throws Exception {
        // Get all input field value
        int capacity = 0;

        try {
            capacity = Integer.parseInt(capacity_field.getText());
        }
        catch(Exception e) {} // Ignore parsing error

        String dbServerURL = db_server_url_field.getText().replace("http:", "")
            .replace("http:", "").replaceAll("/", "")
            .replaceAll("\\s{1,}", "");
        String dbUsername = db_username_field.getText();
        String dbPassword = db_password_field.getText();

        // Check if input field has value(s)
        if (capacity != 0 && !dbServerURL.isEmpty() && !dbUsername.isEmpty()) {
            // Check if database url is not valid
            if (!dbServerURL.matches("^[a-zA-Z0-9]{1,}(:[0-9]{1,5})?$")) {
                AlertBoxView.showAlert(AlertType.ERROR, "Database Server URL", "Database Server URL format is not valid");
                return;
            }

            // Get json file data
            Map<String, Object> map = JSONFile.toMap();

            map.put("house_capacity", capacity);
            map.put("db_srv_url", dbServerURL);
            map.put("db_usr", dbUsername);
            map.put("db_pw", dbPassword);
        
            // Write json file
            JSONFile.write(map);

            // Redirect to main page
            Main.showPage("./views/loadingView.fxml", true);
        }
    }
}