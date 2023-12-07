package app.screens.mainpage.subpages.edit;

import java.util.Map;

import app.screens.mainpage.MainPageController;
import app.Main;
// Model(s)
import app.models.JSONFile;

// Javafx lib
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class EditController {
    // House field fxid(s)
    @FXML
    private TextField name_field;
    @FXML
    private TextField capacity_field;

    // Database field fxid(s)
    @FXML
    private TextField db_url_field;
    @FXML
    private TextField db_username_field;
    @FXML
    private TextField db_password_field;

    // Button fxid(s)
    @FXML
    private Button cancel_btn;
    @FXML
    private Button save_btn;

    private Map<String, Object> _map = JSONFile.toMap();

    private void _setOldVal(TextField field, String oldVal) {
        field.setText(oldVal);
        field.setPromptText(oldVal);
    }

    @FXML
    private void initialize() {
        // Set all field according to old config data
        _setOldVal(name_field, (String) _map.get("house_name"));
        _setOldVal(capacity_field, Long.toString((Long) _map.get("house_capacity")));
        _setOldVal(db_url_field, (String) _map.get("db_url"));
        _setOldVal(db_username_field, (String) _map.get("db_usr"));
        _setOldVal(db_password_field, (String) _map.get("db_pw"));
    }

    private void _showConfigPage() throws Exception {
        // Load main page fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../mainpage.fxml"));
        Parent root = loader.load();

        MainPageController mainController = loader.getController();

        mainController.implementConfig((MouseEvent) null);

        // Redirect to config page
        mainController.setSubpage("./subpages/config/config.fxml");

        Main.getAppStage().setScene(new Scene(root));
    }

    @FXML
    private void _cancelEditing(ActionEvent event) throws Exception {
        _showConfigPage();
    }

    @FXML
    private void _saveConfig(ActionEvent event) throws Exception  {
        String houseName = name_field.getText().trim();
        int houseCapacity = 0;

        try {
            houseCapacity = Integer.parseInt(capacity_field.getText());
        }
        catch(Exception e) {} // Ignore parse error

        String dbURL = db_url_field.getText().replaceAll(" ", "");
        String dbUsername = db_username_field.getText().trim();
        String dbPassword = db_password_field.getText();

        if (!houseName.isEmpty() && houseCapacity != 0 && 
            !dbURL.isEmpty() && !dbUsername.isEmpty()) {
            // Check if new house_capacity is valid (not less than current total patient)
            // Code here

            // Replace old map value to new value
            _map.put("house_name", houseName);
            _map.put("house_capacity", houseCapacity);
            _map.put("db_url", dbURL);
            _map.put("db_usr", dbUsername);
            _map.put("db_pw", dbPassword);

            // Write json file with new map value
            JSONFile.write(_map);

            _showConfigPage();
        }
    }
}
