package app.controllers;

import app.Main;
import json.JSONFile;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

// Javafx lib
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
    private Label db_server_url;
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
        db_server_url.setText((String) map.get("db_srv_url"));
        db_username.setText((String) map.get("db_usr"));
        String password = (String) map.get("db_pw");

        // Database password can be empty, therefore put "-" if empty
        if (!password.isEmpty()) {db_password.setText(password);}
        else {db_password.setText("-");}
    }

    @FXML
    private void _editBtnHandler(ActionEvent event) throws Exception {
        // Load main page fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/homeView.fxml"));
        Parent root = loader.load();

        // Retrieve controller
        HomeController homeController = loader.getController();

        homeController.configHandler(null);
        homeController.setSubpage("../views/editSubView.fxml");

        // Set new scene
        Main.getAppStage().setScene(new Scene(root));
    }
}
