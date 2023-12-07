package app.screens.mainpage.subpages.config;

import java.util.Map;

import app.screens.mainpage.MainPageController;
import app.Main;
// Model(s)
import app.models.JSONFile;

// Javafx lib
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;

public class ConfigController {
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

    @FXML
    void _gotoEditPage(ActionEvent event) throws Exception {
        // Load main page fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../mainpage.fxml"));
        Parent root = loader.load();

        MainPageController mainController = loader.getController();

        mainController.implementConfig((MouseEvent) null);

        // Redirect to edit page
        mainController.setSubpage("./subpages/edit/edit.fxml");

        Main.getAppStage().setScene(new Scene(root));
    }
}
