package app.screens.mainpage;

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

import java.net.URL;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

public class MainPageController implements Initializable {
    // Layout(s) fxid(s)
    @FXML
    private BorderPane main_layout;
    @FXML
    private VBox menu_container;

    // Menu(s) fxid(s)
    @FXML
    private HBox dashboard;
    @FXML
    private HBox client_menu;
    @FXML
    private HBox caretaker_menu;
    @FXML
    private HBox donation;

    // Colors for selected and unselected menu
    private String selectedColor =  "#2eb2ee";
    private String unselectedBarColor = "#ffffff";
    private String unselectedLabelColor = "#89898a";

    public void setSubpage(String fxmlPath) {
        try {
            // Load fxml file
            Parent page = FXMLLoader.load(getClass().getResource(fxmlPath));

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
        setSubpage("./subpages/dashboard/dashboard.fxml");
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

    // Handler for dashboard menu
    @FXML
    private void _implementDashboard(MouseEvent event) {
        _setMenuState(dashboard);

        setSubpage("./subpages/dashboard/dashboard.fxml");
    }

    // Handler for client menu
    @FXML
    private void _implementClientMenu(MouseEvent event) {
        _setMenuState(client_menu);
    }

    // Handler for caretaker menu
    @FXML
    private void _implementCaretakerMenu(MouseEvent event) {
        _setMenuState(caretaker_menu);
    }

    // Handler for donation menu
    @FXML
    private void _implementDonation(MouseEvent event) {
        _setMenuState(donation);
    }

    // Hander for config menu
    @FXML
    public void implementConfig(MouseEvent event) {
        _setMenuState((HBox) null);

        setSubpage("./subpages/config/config.fxml");
    }
}
