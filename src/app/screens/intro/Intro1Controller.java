package app.screens.intro;

import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.LinkedHashMap;

import app.Main;
import app.services.JSONFile;

// Javafx lib
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class Intro1Controller implements Initializable {
    @FXML
    private TextField house_name_field;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set house name field value, if user have save it before
        String currHouseName = Objects.toString(
            JSONFile.toMap().get("house_name"), "");

        if (!currHouseName.isEmpty()) {
            house_name_field.setText(currHouseName);
        }
    }

    private void _proceed() throws Exception {
        String houseName = house_name_field.getText().trim();

        if (!houseName.isEmpty()) {
            Map<String, Object> map = new LinkedHashMap<>();

            map.put("house_name", houseName);

            // Write json file
            JSONFile.write(map);

            // Redirect to intro 2 page
            Main.showPage("./screens/intro/intro2.fxml", false);
        }
    }

    // Handler for key enter in house_name_field
    @FXML
    private void _proceedOnEnter(KeyEvent event) throws Exception {
        if (event.getCode().equals(KeyCode.ENTER)) {_proceed();}
    }

    // Handler for next_btn
    @FXML
    private void _proceedOnClick(MouseEvent event) throws Exception {
        _proceed();
    }
}
