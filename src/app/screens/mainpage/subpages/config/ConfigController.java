package app.screens.mainpage.subpages.config;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import app.screens.mainpage.MainPageController;
import app.services.JSONFile;
import app.Main;
// Javafx lib
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;

public class ConfigController implements Initializable {
    // House profile fxid(s)
    @FXML
    private Label house_name;
    @FXML
    private Label house_capacity;

    // Database fxid(s)
    @FXML
    private Label db_password;
    @FXML
    private Label db_url;
    @FXML
    private Label db_username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set label text from json data
        Map<String, Object> map = JSONFile.toMap();

        // House profile
        house_name.setText((String) map.get("house_name"));
        house_capacity.setText(Long.toString((Long) map.get("house_capacity")));

        // Database
        db_url.setText((String) map.get("db_url"));
        db_username.setText((String) map.get("db_usr"));
        String password = (String) map.get("db_pw");

        // Database password can be empty, therefore put "-" if empty
        if (!password.isEmpty()) {db_password.setText(password);}
        else {db_password.setText("-");}
    }

    // Handler for edit_btn
    @FXML
    void _gotoEditPage(ActionEvent event) throws Exception {
        // Load main page fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../mainpage.fxml"));
        Parent root = loader.load();

        // Retrieve main page controller
        MainPageController mainController = loader.getController();

        // Call config menu handler
        mainController.implementConfig((MouseEvent) null);
        
        mainController.setSubpage("./subpages/edit/edit.fxml");

        // Set new scene
        Main.getAppStage().setScene(new Scene(root));
    }
}
