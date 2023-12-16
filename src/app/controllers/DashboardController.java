package app.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Collections;

// Jsonfile
import json.JSONFile;
import app.Main;
// Model(s)
import app.models.Caretaker;
import app.models.Item;
import app.models.Patient;

// Javafx lib
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DashboardController implements Initializable {
    // Label fxid(s)
    @FXML
    private Label filled_cap_label;
    @FXML
    private Label patient_total;
    @FXML
    private Label avail_caretaker;

    // Layout fxid(s)
    @FXML
    private VBox remain_supply_content;

    private int _patientTotal = 0;
    private int _availCaretaker = 0;
    private ObservableList<Item> _itemList = FXCollections.observableArrayList();

    public DashboardController() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                _patientTotal = Patient.fetch().size();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                _availCaretaker = Caretaker.fetch().size();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                _itemList = Item.fetch();

                // Sort list asc
                Collections.sort(_itemList, new Comparator<Item>() {
                    @Override
                    public int compare(Item item1, Item item2) {
                        if (item1.getQuantity() == item2.getQuantity()) {
                            return 0;
                        }
                        else if (item1.getQuantity() > item2.getQuantity()) {
                            return 1;
                        }

                        return -1;
                    }
                });
            }
        }).start();
    }

    private HBox _itemCard(String name, int quantity) {
        HBox card = new HBox() {{
            setMaxWidth(Double.POSITIVE_INFINITY);
            setStyle("-fx-background-color: #ffffff;" + 
                "-fx-padding: 11;" + 
                "-fx-background-radius: 10;");
        }};

        Label itemName = new Label() {{
            setText(name);
            setMaxWidth(Double.POSITIVE_INFINITY);
            setAlignment(Pos.CENTER_LEFT);
        }};

        Label itemQuantity = new Label() {{
            setText(Integer.toString(quantity) + " left");

            if (quantity == 0) {
                setStyle("-fx-text-fill: #ff0000;");
            }

            setMaxWidth(Double.POSITIVE_INFINITY);
            setAlignment(Pos.CENTER_RIGHT);
        }};

        // Set HGrow property
        HBox.setHgrow(itemName, Priority.ALWAYS);

        // Add labels into hbox card
        card.getChildren().addAll(itemName, itemQuantity);

        return card;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            Map<String, Object> map = JSONFile.toMap();

            // Set label text
            filled_cap_label.setText(String.format("%d/%d", _patientTotal, map.get("house_capacity")));
            patient_total.setText(Integer.toString(_patientTotal));
            avail_caretaker.setText(Integer.toString(_availCaretaker));

            int n = _itemList.size();

            // Set remaining supply content
            for (int i = 0; i < 4 && i < n; i++) {
                Item obj = _itemList.get(i);

                remain_supply_content.getChildren()
                    .add(_itemCard(obj.getName(), obj.getQuantity()));
            }
        });
    }

    @FXML
    private void _redirectToSupply(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/homeView.fxml"));
        Parent root = loader.load();

        HomeController controller = loader.getController();

        controller.supplyListHandler(null);

        Stage appStage = Main.getAppStage();

        appStage.setScene(new Scene(root));

        appStage.show();
    }
}
