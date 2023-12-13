package app.controllers;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

// Model(s)
import app.models.Donation;

// Javafx lib
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class DonationModalController implements Initializable {
    @FXML
    private Label title_label;

    // Input field fxid(s)
    @FXML
    private TextField donator_field;
    @FXML
    private TextField item_field;
    @FXML
    private TextField quantity_field;
    @FXML
    private DatePicker date_field;

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
                title_label.setText("Add New Donation");
            }
            else {
                title_label.setText("Update Donation");

                Donation obj = DonationListController.getList().get(_idx);

                // Initialize field value(s)
                _setOldVal(donator_field, obj.getDonatorName());
                _setOldVal(item_field, obj.getItemName());
                _setOldVal(quantity_field, Integer.toString(obj.getItemQuantity()));
                
                date_field.setValue(obj.getDate().toLocalDate());

                obj = null;

                System.gc();
            }
        });

        // Listen to donator_field on value change
        donator_field.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldVal, String newVal) {
                if (!newVal.matches("[a-zA-Z ,.']*")) {
                    donator_field.setText(newVal.replaceAll("[^[a-zA-Z ,.']]", ""));
                }
                // Validate string length
                else if (newVal.length() > 255) {
                    donator_field.setText(newVal.substring(0, 255));
                }
            }
        });

        // Listen to item_field on value change
        item_field.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldVal, String newVal) {
                if (!newVal.matches("[a-z ]*")) {
                    item_field.setText(newVal.replaceAll("[^[a-z ]]", ""));
                }
                // Validate string length
                else if (newVal.length() > 255) {
                    item_field.setText(newVal.substring(0, 255));
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
        String donator = donator_field.getText().trim()
            .replaceAll("\\s{2,}", " ");
        String item = item_field.getText().trim()
            .replaceAll("\\s{2,}", " ");
        int quantity = 0;

        try {
            quantity = Integer.parseInt(quantity_field.getText());
        }
        catch (Exception e) {} // Ignore parsing error

        LocalDate date = date_field.getValue();

        if (!donator.isEmpty() && !item.isEmpty() && quantity != 0 && date != null) {
            // Insert action
            if (_action.equals("insert")) {
                new Donation(donator, item, quantity, Date.valueOf(date)).insert();
            }
            // Update action
            else {
                Donation obj = DonationListController.getList().get(_idx);

                obj.update(new Donation(donator, item, quantity, Date.valueOf(date)));
            }

            _modalStage.close();
        }
    }
}
