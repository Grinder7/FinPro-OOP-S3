package app;

import java.io.File;
import java.sql.SQLException;
import java.util.Map;

import app.controllers.HomeController;
import database.DBConnection;

// Javafx lib
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import json.JSONFile;

public class Main extends Application {
    private static Stage _appStage;

    public static void showPage(String fxmlPath, boolean resizeable) throws Exception {
        // Load fxml file
        Parent root = FXMLLoader.load(Main.class.getResource(fxmlPath));

        Scene scene = new Scene(root);

        // Set scene
        _appStage.setScene(scene);

        // Set stage properties
        _appStage.centerOnScreen();
        _appStage.setResizable(resizeable);

        // Show stage
        _appStage.show();
    }

    private boolean _isFresh() { // Method to tell if user first time use app
        try {
            // Open json file
            File file = new File(JSONFile.path);

            // Create new file if file is missing
            file.createNewFile();

            // Get data from json file
            Map<String, Object> map = JSONFile.toMap();

            if (!map.isEmpty()) {
                // Check if all data required already made
                return !(map.get("house_name") != null && map.get("house_capacity") != null 
                    && map.get("db_srv_url") != null && map.get("db_usr") != null && 
                    map.get("db_pw") != null);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        return true; // Cannot reach this code
    }

    public static Stage getAppStage() {return _appStage;}

    @Override
    public void start(Stage primaryStage) throws Exception {
        _appStage = primaryStage;

        // Program icon
        _appStage.getIcons().add(new Image(Main.class.getResourceAsStream("../assets/ico.png")));
        
        // Set program title
        _appStage.setTitle("Disabled");

        _appStage.setOnCloseRequest(e -> {
            if (DBConnection.isEstablished()) {
                try {
                    DBConnection.getConnection().close();
                }
                catch (SQLException s) {
                    s.printStackTrace();
                }
            }

            HomeController.stopDBPolling();
        });

        if (_isFresh()) {
            showPage("./views/intro1View.fxml", false);
        }
        else {
            showPage("./views/loadingView.fxml", false);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}