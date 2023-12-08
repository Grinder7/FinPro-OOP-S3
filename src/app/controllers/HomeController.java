package app.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import database.DBConnection;

// Javafx lib
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

public class HomeController implements Initializable {
    // Layout(s) fxid(s)
    @FXML
    private BorderPane main_layout;
    @FXML
    private VBox menu_container;

    // Menu(s) fxid(s)
    @FXML
    private HBox dashboard;
    @FXML
    private HBox patient_menu;
    @FXML
    private HBox caretaker_menu;
    @FXML
    private HBox donation;

    // Colors for selected and unselected menu
    private String selectedColor =  "#2eb2ee";
    private String unselectedBarColor = "#ffffff";
    private String unselectedLabelColor = "#89898a";

    public void setSubpage(String fxmlPath) {
        // Array list contains pages path that can bypass db connection
        ArrayList<String> whitelist = new ArrayList<>(Arrays.asList(
            "../views/configSubView.fxml", "../views/editSubView.fxml"
        )); 

        try {
            Parent page = null;

            // Load fxml file, by checking connection to database
            if (!DBConnection.getConnEstablished() && !whitelist.contains(fxmlPath)) {
                page = FXMLLoader.load(getClass().getResource("../views/dberrorSubView.fxml"));
            }
            else {
                page = FXMLLoader.load(getClass().getResource(fxmlPath));
            }

            main_layout.setCenter(page);
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set dashboard as initial subpage
        setSubpage("../views/dashboardSubView.fxml");
    }

    private void _setMenuState(HBox clickedMenu) {
        ObservableList<Node> menus = menu_container.getChildren();

        // Set all menu state according to it's selected state
        for (Node menu: menus) {
            ObservableList<Node> menuChild = ((HBox) menu).getChildren();
            ObservableList<Node> labelChild = ((HBox) menuChild.get(1)).getChildren();

            if (menu == clickedMenu) { // Selected
                // Change bar background color
                ((AnchorPane) menuChild.get(0)).setStyle("-fx-background-color: " + selectedColor + ";");

                // Change icon fill color
                ((FontAwesomeIconView) labelChild.get(0)).setFill(Color.valueOf(selectedColor));
                
                // Change label font color
                Label label = (Label) labelChild.get(1);
                label.setTextFill(Color.valueOf(selectedColor));
                label.setFont(Font.font("System", FontWeight.BOLD, 16));
            }
            else { // Unselected
                // Change bar background color
                ((AnchorPane) menuChild.get(0)).setStyle("-fx-background-color: " + unselectedBarColor + ";");

                // Change icon fill color
                ((FontAwesomeIconView) labelChild.get(0)).setFill(Color.valueOf(unselectedLabelColor));

                // Change label font color
                Label label = (Label) labelChild.get(1);
                label.setTextFill(Color.valueOf(unselectedLabelColor));
                label.setFont(Font.font("System", FontWeight.NORMAL, 16));
            }
        }
    }

    @FXML
    private void _dashboardHandler(MouseEvent event) {
        _setMenuState(dashboard);

        setSubpage("../views/dashboardSubView.fxml");
    }

    @FXML
    private void _patientMenuHandler(MouseEvent event) {
        _setMenuState(patient_menu);
    }

    @FXML
    private void _caretakerMenuHandler(MouseEvent event) {
        _setMenuState(caretaker_menu);
    }

    @FXML
    private void _donationHandler(MouseEvent event) {
        _setMenuState(donation);
    }

    @FXML
    public void configHandler(MouseEvent event) {
        _setMenuState((HBox) null);

        setSubpage("../views/configSubView.fxml");
    }
}
