package app.screens.settings;

import java.util.Map;

// Model(s)
import app.models.JSONFile;

// Javafx lib
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SettingsController {
    // House profile fxid(s)
    @FXML
    private Label house_name;
    @FXML
    private Label house_capacity;

    // Database configuration fxid(s)
    @FXML
    private Label db_password;
    @FXML
    private Label db_url;
    @FXML
    private Label db_username;

    @FXML
    private void initialize() {
        Map<String, Object> map = JSONFile.toMap();

        // House profile
        // Set house name value
        house_name.setText((String) map.get("house_name"));
        // Set house capacity value
        house_capacity.setText(Long.toString((Long) map.get("house_capacity")));

        // Database configuration
        // Set database url value
        db_url.setText((String) map.get("db_url"));
        // Set database username value
        db_username.setText((String) map.get("db_usr"));
        // Set database password value
        // Database password can be empty, therefore put "-" if empty
        String password = (String) map.get("db_pw");

        if (!password.isEmpty()) {
            db_password.setText(password);
        }
        else {
            db_password.setText("-");
        }
    }
}
