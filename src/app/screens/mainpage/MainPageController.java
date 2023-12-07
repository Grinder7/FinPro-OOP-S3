package app.screens.mainpage;

// Javafx lib
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class MainPageController {
    @FXML
    private BorderPane main_layout;
    @FXML
    private VBox menu_container;
    @FXML
    private HBox caretaker_menu;
    @FXML
    private HBox client_menu;
    @FXML
    private HBox dashboard;
    @FXML
    private HBox donation;

    private String selectedColor =  "#2eb2ee";
    private String unselectedBarColor = "#ffffff";
    private String unselectedLabelColor = "#89898a";

    public void initialize() {
        _setSubpage("./subpages/dashboard/dashboard.fxml");
    }

    @FXML
    private void _setMenuState(HBox clickedMenu) {
        ObservableList<Node> menus = menu_container.getChildren();

        for (Node menu: menus) {
            ObservableList<Node> menuChild = ((HBox) menu).getChildren();
            ObservableList<Node> labelChild = ((HBox) menuChild.get(1)).getChildren();

            if (menu == clickedMenu) { // Selected
                ((AnchorPane) menuChild.get(0)).setStyle("-fx-background-color: " + selectedColor + ";");
                ((FontAwesomeIconView) labelChild.get(0)).setFill(Color.valueOf(selectedColor));
                
                Label label = (Label) labelChild.get(1);
                label.setTextFill(Color.valueOf(selectedColor));
                label.setFont(Font.font("System", FontWeight.BOLD, 16));
            }
            else { // Unselected
                ((AnchorPane) menuChild.get(0)).setStyle("-fx-background-color: " + unselectedBarColor + ";");
                ((FontAwesomeIconView) labelChild.get(0)).setFill(Color.valueOf(unselectedLabelColor));

                Label label = (Label) labelChild.get(1);
                label.setTextFill(Color.valueOf(unselectedLabelColor));
                label.setFont(Font.font("System", FontWeight.NORMAL, 16));
            }
        }
    }

    private void _setSubpage(String fxmlPath) {
        try {
            Parent page = FXMLLoader.load(getClass().getResource(fxmlPath));

            main_layout.setCenter(page);
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @FXML
    private void _implementDashboard(MouseEvent event) {
        _setMenuState(dashboard);

        _setSubpage("./subpages/dashboard/dashboard.fxml");
    }

    @FXML
    private void _implementClientMenu(MouseEvent event) {
        _setMenuState(client_menu);
    }

    @FXML
    private void _implementCaretakerMenu(MouseEvent event) {
        _setMenuState(caretaker_menu);
    }

    @FXML
    private void _implementDonation(MouseEvent event) {
        _setMenuState(donation);
    }

    @FXML
    private void _implementSettings(MouseEvent event) {
        _setMenuState((HBox) null);

        _setSubpage("../settings/settings.fxml");
    }
}
