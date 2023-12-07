package app.screens.intro;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import app.Main;

// Service(s)
import app.services.JSONFile;

// Javafx lib
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class Intro2Controller implements Initializable {
    // Input field fxid(s)
    @FXML
    private TextField capacity_field;
    @FXML
    private TextField db_url_field;
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

    // Handler for back_btn
    @FXML
    private void _back() throws Exception {
        Main.showPage("./screens/intro/intro1.fxml", false);
    }

    // Handler for next_btn
    @FXML
    private void _proceed() throws Exception {
        // Get all input field value
        int capacity = 0;

        try {
            capacity = Integer.parseInt(capacity_field.getText());
        }
        catch(Exception e) {} // Ignore parsing error

        String dbURL = db_url_field.getText().replaceAll(" ", "");
        String dbUsername = db_username_field.getText().trim();
        String dbPassword = db_password_field.getText();

        // Check if input field has value(s)
        if (capacity != 0 && !dbURL.isEmpty() && !dbUsername.isEmpty()) {
            // Get json file data
            Map<String, Object> map = JSONFile.toMap();

            map.put("house_capacity", capacity);
            map.put("db_url", dbURL);
            map.put("db_usr", dbUsername);
            map.put("db_pw", dbPassword);
        
            // Write json file
            JSONFile.write(map);

            // Redirect to main page
            Main.showPage("./screens/mainpage/mainpage.fxml", true);
        }
    }
}