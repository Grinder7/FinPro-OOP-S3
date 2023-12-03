package app.screens.intro;

import java.util.Map;

import java.util.HashMap;
import java.io.FileWriter;

import app.Main;

// Javafx lib
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// JSON lib
import org.json.simple.JSONObject;

public class IntroController {
    @FXML
    private TextField field;
    @FXML
    private HBox next_btn;

    private final void _redirectToMainPage() throws Exception {
        // Get current stage
        Stage appStage = Main.getAppStage();

        // Remove action bar
        appStage.initStyle(StageStyle.UNDECORATED);

        // Switch stage scene to main_page
        Main.showMainPage(
            FXMLLoader.load(getClass().getResource("../main_page/main_page.fxml"))
        );
        
        // Set main_page as current scene
        appStage.show();
    }

    @FXML
    void register(MouseEvent event) {
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
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
