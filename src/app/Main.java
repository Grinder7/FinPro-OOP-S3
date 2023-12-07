package app;

import java.io.File;
import java.io.FileReader;
import java.util.Map;

// Model(s)
import app.models.JSONFile;

// Javafx lib
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage _appStage;

    public static void showPage(String fxmlPath, boolean resizeable) throws Exception {
        Parent root = FXMLLoader.load(Main.class.getResource(fxmlPath));

        // Set scene
        _appStage.setScene(new Scene(root));

        _appStage.centerOnScreen();

        _appStage.setResizable(resizeable);

        // Show stage
        _appStage.show();

        // Set minimum size, therefore user can't shrink window size less than mininum size
        _appStage.setMinHeight(_appStage.getHeight());
        _appStage.setMinWidth(_appStage.getWidth());
    }

    private boolean _isFresh() {
        try {
            File file = new File("./src/app/data.json");

            // Create new file if file is missing
            file.createNewFile();

            FileReader jsonFile = new FileReader("./src/app/data.json");

            // Check if jsonFile is not empty
            if (jsonFile.read() != -1) {
                jsonFile.close();

                Map<String, Object> map = JSONFile.toMap();

                // Check if all data required already made
                return !(map.get("house_name") != null && map.get("house_capacity") != null &&
                    map.get("db_url") != null && map.get("db_usr") != null && map.get("db_pw") != null);
            }
            else {
                jsonFile.close();
                return true;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        return true;
    }

    public static void setAppStage(Stage s) {_appStage = s;}

    public static Stage              getAppStage() {return _appStage;}

    @Override
    public void start(Stage primaryStage) throws Exception {
        setAppStage(primaryStage);

        // Program icon
        // _appStage.getIcons().add(new Image(Main.class.getResourceAsStream("./assets/ico.png")));
        
        // Program title
        _appStage.setTitle("Demo");

        if (_isFresh()) {
            showPage("./screens/intro/intro1.fxml", false);
        }
        else {
            showPage("./screens/mainpage/mainpage.fxml", true);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}