package app.screens.intro;

import java.util.Map;

import app.Main;

// Model(s)
import app.models.JSONFile;

// Javafx lib
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class Intro2Controller {
    // fxid(s)
    @FXML
    private TextField capacity_field;
    @FXML
    private TextField db_url_field;
    @FXML
    private TextField db_username_field;
    @FXML
    private TextField db_password_field;
    @FXML
    private HBox back_btn;
    @FXML
    private HBox next_btn2;

    @FXML
    private void initialize() {
        // Listen to capacity_field on value change
        capacity_field.textProperty().addListener(new ChangeListener<String>() {
            // Remove non-number character
            public void changed(ObservableValue<? extends String> observable, String oldVal, String newVal) {
                if (!newVal.matches("\\d*")) {
                    capacity_field.setText(newVal.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    @FXML
    private void _back() throws Exception {
        Main.showPage(
            FXMLLoader.load(getClass().getResource("./intro1.fxml")), false);
    }

    @FXML
    private void _proceed() throws Exception {
        // Get capacity_field value
        int capacity = 0;

        try {
            capacity = Integer.parseInt(capacity_field.getText());
        }
        catch(Exception e) {} // Ignore parse error

        // Get db_url_field value
        String dbURL = db_url_field.getText();

        // Get db_username_field value
        String dbUsername = db_username_field.getText();

        // Get db_password_field value
        String dbPassword = db_password_field.getText();

        if (capacity != 0 && !dbURL.isEmpty() && !dbUsername.isEmpty()) {
            Map<String, Object> map = JSONFile.toMap();

            map.put("house_capacity", capacity); // Put house capacity to map
            map.put("db_url", dbURL); // Put database url to map
            map.put("db_usr", dbUsername); // Put database username to map
            map.put("db_pw", dbPassword); // Put database password to map
        
            // Write json file
            JSONFile.write(map);

            // Redirect to main page
            Main.showPage(
                FXMLLoader.load(getClass().getResource("../mainpage/mainpage.fxml")), true);
        }
    }
}