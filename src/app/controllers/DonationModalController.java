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

    public void setStage(Stage stage) {_modalStage = stage;}

    @Override
    public void initialize(URL location, ResourceBundle recourses) {
        // Intialize label and button text
        title_label.setText("Add New Donation");

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
            new Donation(donator, item, quantity, Date.valueOf(date)).insert();

            _modalStage.close();
        }
    }
}
