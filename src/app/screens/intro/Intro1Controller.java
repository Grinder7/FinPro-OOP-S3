package app.screens.intro;

import app.Main;

import java.util.Map;
import java.util.Objects;
import java.util.LinkedHashMap;

// Model(s)
import app.models.JSONFile;

// Javafx lib
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class Intro1Controller {
    // fxid(s)
    @FXML
    private TextField house_name_field;
    @FXML
    private HBox next_btn;

    @FXML
    private void initialize() {
        String currHouseName = Objects.toString(JSONFile.toMap().get("house_name"), "") ;

        if (!currHouseName.isEmpty()) {
            // Set input value to saved house name
            house_name_field.setText(currHouseName);
        }
    }

    private void _proceed() throws Exception {
        // Get house_name value
        String houseName = house_name_field.getText();

        if (!houseName.isEmpty()) {
            Map<String, Object> map = new LinkedHashMap<>();

            // Put house_name in map
            map.put("house_name", houseName);

            // Write json file
            JSONFile.write(map);

            // Redirect to intro 2 page
            Main.showPage("./screens/intro/intro2.fxml", false);
        }
    }

    @FXML
    private void _proceedOnEnter(KeyEvent event) throws Exception {
        if (event.getCode().equals(KeyCode.ENTER)) {_proceed();}
    }

    @FXML
    private void _proceedOnClick(MouseEvent event) throws Exception {
        _proceed();
    }
}
