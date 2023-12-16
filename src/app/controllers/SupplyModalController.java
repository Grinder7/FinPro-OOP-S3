package app.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

// Model(s)
import app.models.Item;

import app.views.AlertBoxView;

import database.DBConnection;

// Javafx lib(s)
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class SupplyModalController implements Initializable {
    @FXML
    private Label title_label;

    // Field fxid(s)
    @FXML
    private TextField name_field;
    @FXML
    private TextField quantity_field;

    @FXML
    private Button save_btn;

    private Stage _modalStage;
    private String _action;
    private int _idx;

    public void setStage(Stage stage) {_modalStage = stage;}

    public void setAction(String action) {_action = action;}

    public void setIdx(int idx) {_idx = idx;}

    private void _setOldVal(TextField field, String val) {
        field.setText(val);
        field.setPromptText(val);
    }

    @Override
    public void initialize(URL location, ResourceBundle recourses) {
        // Intialize label and button text
        Platform.runLater(() -> {
            if (_action.equals("insert")) {
                title_label.setText("Add New Item");
            }
            else {
                title_label.setText("Update Item");

                Item obj =SupplyListController.getList().get(_idx);

                // Initialize field value(s)
                _setOldVal(name_field, obj.getName());
                _setOldVal(quantity_field, Integer.toString(obj.getQuantity()));
                
                obj = null;

                System.gc();
            }
        });

        // Listen to name_field on value change
        name_field.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldVal, String newVal) {
                if (!newVal.matches("[a-z ,.']*")) {
                    name_field.setText(newVal.replaceAll("[^[a-z ]]", ""));
                }
                // Validate string length
                else if (newVal.length() > 255) {
                    name_field.setText(newVal.substring(0, 255));
                }
            }
        });

        // Listen to quantity_field on value change
        quantity_field.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldVal, String newVal) {
                // Remove non-number character
                if (!newVal.matches("\\d*")) {
                    quantity_field.setText(newVal.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    @FXML
    private void _saveBtnHandler(ActionEvent event) {
        // Get all field value(s)
        String name = name_field.getText().trim()
            .replaceAll("\\s{2,}", " ");
        int quantity = -1;

        try {
            quantity = Integer.parseInt(quantity_field.getText());
        }
        catch (Exception e) {} // Ignore parsing error

        if (!name.isEmpty() && quantity != -1) {
            // Insert action
            if (_action.equals("insert")) {
                try (PreparedStatement stmt = DBConnection.getConnection()
                    .prepareStatement("SELECT itemId FROM `supply` WHERE itemName = ?;");) {
                    stmt.setString(1, name);

                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        AlertBoxView.showAlert(AlertType.ERROR, "Duplicate Item", "Item already recorded, please enter other item");
                        
                        rs.close();

                        return;
                    }
                    else {new Item(name, quantity).insert();}

                    rs.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
            // Update action
            else {
                Item obj = SupplyListController.getList().get(_idx);

                obj.update(new Item(name, quantity));
            }

            _modalStage.close();
        }
    }
}
