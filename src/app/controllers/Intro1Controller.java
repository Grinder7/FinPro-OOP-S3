package app.controllers;

import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.HashMap;

import app.Main;

// Json file
import json.JSONFile;

// Javafx lib
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class Intro1Controller implements Initializable {
    // Input field fxid(s)
    @FXML
    private TextField house_name_field;

    private void _proceed() throws Exception {
        String houseName = house_name_field.getText().trim() // Remove left and right whitespaces
            .replaceAll("\\s{2,}", " "); // Shorten 2 or more whitespace into 1 char

        if (!houseName.isEmpty()) {
            Map<String, Object> map = new HashMap<>();

            map.put("house_name", houseName);

            // Write json file
            JSONFile.write(map);

            // Redirect to intro 2 page
            Main.showPage("./views/intro2View.fxml", false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set house name field value, if user have save it before
        String currHouseName = Objects.toString(
            JSONFile.toMap().get("house_name"), "");

        if (!currHouseName.isEmpty()) {
            house_name_field.setText(currHouseName);
        }
    }

    @FXML
    private void _houseNameFieldHandler(KeyEvent event) throws Exception {
        if (event.getCode().equals(KeyCode.ENTER)) {_proceed();}
    }

    @FXML
    private void _nextBtnHandler(MouseEvent event) throws Exception {
        _proceed();
    }
}
