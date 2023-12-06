package app.screens.intro;

import app.Main;

import java.util.Map;
import java.util.HashMap;
import java.io.FileWriter;

// Javafx lib
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

// JSON lib
import org.json.simple.JSONObject;

public class IntroController {
    @FXML
    private TextField field;
    @FXML
    private HBox next_btn;

    private void _redirectToMainPage() throws Exception {
        // Get current stage
        Stage appStage = Main.getAppStage();

        // Switch stage scene to main_page
        Main.showMainPage(
            FXMLLoader.load(getClass().getResource("../mainpage/mainpage.fxml"))
        );
        
        // Set main_page as current scene
        appStage.show();
    }

    private void register() {
        // Retrieve string from input field
        String houseName = field.getText();

        if (!houseName.isEmpty()) {
            try {
                Map<String, Object> map = new HashMap<>();

                // Put house name data
                map.put("house_name", houseName);

                JSONObject jsonObj = new JSONObject(map);

                // Write json file
                try (FileWriter file = new FileWriter("./src/app/data.json")) {
                    file.write(jsonObj.toString());
                    file.flush();

                    _redirectToMainPage();
                }
                catch(Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
            catch(Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    @FXML
    private void registerOnEnter(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {register();}
    }

    @FXML
    private void registerOnClick(MouseEvent event) {register();}
}
