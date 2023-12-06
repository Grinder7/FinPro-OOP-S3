package app;

import java.io.File;
import java.io.FileReader;
import java.util.Objects;

// JSON lib
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

// Javafx lib
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage _appStage;

    private static double _xOffset = 0;
    private static double _yOffset = 0;

    private void _showIntroPage(Parent root) throws Exception {
        // Set scene
        _appStage.setScene(new Scene(root));

        // On mouse pressed event handler
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                _xOffset = event.getSceneX();
                _yOffset = event.getSceneY();
            }
        });

        // On mouse dragged event handler
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                _appStage.setX(event.getScreenX() - _xOffset);
                _appStage.setY(event.getScreenY() - _yOffset);
            }
        });
    }

    public static void showMainPage(Parent root) throws Exception {
        // Set scene
        _appStage.setScene(new Scene(root));

        _appStage.centerOnScreen();

        _appStage.setMinHeight(_appStage.getHeight());
        _appStage.setMinWidth(_appStage.getWidth());

        /* // On mouse pressed event handler
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                _xOffset = event.getSceneX();
                _yOffset = event.getSceneY();
            }
        });

        // On mouse dragged event handler
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                _appStage.setX(event.getScreenX() - _xOffset);
                _appStage.setY(event.getScreenY() - _yOffset);
            }
        }); */
    }

    private boolean _isFresh() {
        try {
            File file = new File("./src/app/data.json");

            // Create new file if file is missing
            file.createNewFile();

            FileReader jsonFile = new FileReader("./src/app/data.json");

            // Check if jsonFile is not empty
            if (jsonFile.read() != -1) {
                // Parse jsonFile
                JSONObject jsonObj = (JSONObject) new JSONParser()
                        .parse(new FileReader("./src/app/data.json"));

                // Check if house_name already made
                return Objects.toString((String)jsonObj.get("house_name"), "")
                        .isEmpty();
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

    public static Stage getAppStage() {return _appStage;}

    @Override
    public void start(Stage primaryStage) throws Exception {
        setAppStage(primaryStage);

        if (_isFresh()) {
            _showIntroPage(
                FXMLLoader.load(getClass().getResource("screens/intro/intro.fxml"))
            );
        }
        else {
            showMainPage(
                FXMLLoader.load(getClass().getResource("screens/mainpage/mainpage.fxml"))
            );
        }
        
        // Program icon
        // _appStage.getIcons().add(new Image(Main.class.getResourceAsStream("./assets/ico.png")));
        
        // Program title
        _appStage.setTitle("");

        _appStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}