package app.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private HBox patient_list;
    @FXML
    private HBox caretaker_list;
    @FXML
    private HBox supply_list;
    @FXML
    private HBox donation_list;
    @FXML
    private FontAwesomeIconView config;

    private static BorderPane staticMainLayout;

    private HBox _selectedMenu = null;

    private Map<Node, String> _pages = null;

    // Array list contains pages path that can bypass db connection
    private ArrayList<Node> _whitelistPages = new ArrayList<>() {{
        add(config);
    }};

    // Colors for selected and unselected menu
    private String _selectedColor =  "#2eb2ee";
    private String _unselectedBarColor = "#ffffff";
    private String _unselectedLabelColor = "#89898a";
    private String _hoverColor = "#000000";

    public static void stopDBPolling() {
        try {
            Thread.getAllStackTraces().keySet().stream()
                .filter(t -> t.getName().equals("DBPollingThread"))
                .findFirst().get().interrupt();
        }
        catch (Exception e) {
            System.err.println("No DBPollingThread found");
        }
    }

    public void setMenuState() {
        ObservableList<Node> menus = menu_container.getChildren();

        // Set all menu state according to it's selected state
        for (Node menu : menus) {
            ObservableList<Node> menuChild = ((HBox) menu).getChildren();
            ObservableList<Node> labelChild = ((HBox) menuChild.get(1)).getChildren();

            if (menu == _selectedMenu) { // Selected
                // Change bar background color
                ((AnchorPane) menuChild.get(0)).setStyle("-fx-background-color: " + _selectedColor + ";");

                // Change icon fill color
                ((FontAwesomeIconView) labelChild.get(0)).setFill(Color.valueOf(_selectedColor));
                
                // Change label font color
                Label label = (Label) labelChild.get(1);
                label.setTextFill(Color.valueOf(_selectedColor));
                label.setFont(Font.font("System", FontWeight.BOLD, 16));
            }
            else { // Unselected
                // Change bar background color
                ((AnchorPane) menuChild.get(0)).setStyle("-fx-background-color: " + _unselectedBarColor + ";");

                // Change icon fill color
                ((FontAwesomeIconView) labelChild.get(0)).setFill(Color.valueOf(_unselectedLabelColor));

                // Change label font color
                Label label = (Label) labelChild.get(1);
                label.setTextFill(Color.valueOf(_unselectedLabelColor));
                label.setFont(Font.font("System", FontWeight.NORMAL, 16));
            }
        }
    }

    public void setSubpage(Node selectedMenu) {
        if (_selectedMenu != selectedMenu) {
            stopDBPolling();

            try {
                Parent page = null;

                // Load fxml file, by checking connection to database
                if (!DBConnection.isEstablished() && !_whitelistPages.contains(selectedMenu)) {
                    page = FXMLLoader.load(getClass().getResource("../views/dberrorSubView.fxml"));
                }
                else {
                    page = FXMLLoader.load(getClass().getResource(_pages.get(selectedMenu)));
                }

                main_layout.setCenter(page);
            }
            catch(Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    public static void setCenterPage(Parent page) {staticMainLayout.setCenter(page);}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        staticMainLayout = main_layout;

        _pages = new HashMap<>() {{
            put(dashboard, "../views/dashboardSubView.fxml");
            put(patient_list, "../views/patientlistSubView.fxml");
            put(caretaker_list, "../views/caretakerlistSubView.fxml");
            put(supply_list, "../views/supplylistSubView.fxml");
            put(donation_list, "../views/donationlistSubView.fxml");
            put(config, "../views/configSubView.fxml");
        }};

        // Set dashboard as initial subpage
        _dashboardHandler(null);
    }

    @FXML
    private void _dashboardHandler(MouseEvent event) {
        setSubpage(dashboard);

        _selectedMenu = dashboard;
        setMenuState();
    }

    @FXML
    private void _patientListHandler(MouseEvent event) {
        setSubpage(patient_list);

        _selectedMenu = patient_list;
        setMenuState();
    }

    @FXML
    private void _caretakerListHandler(MouseEvent event) {
        setSubpage(caretaker_list);

        _selectedMenu = caretaker_list;
        setMenuState();
    }

    @FXML
    public void supplyListHandler(MouseEvent event) {
        setSubpage(supply_list);

        _selectedMenu = supply_list;
        setMenuState();
    }

    @FXML
    private void _donationListHandler(MouseEvent event) {
        setSubpage(donation_list);

        _selectedMenu = donation_list;
        setMenuState();
    }

    @FXML
    public void configHandler(MouseEvent event) {
        setSubpage(config);

        _selectedMenu = null;
        setMenuState();
    }

    @FXML
    private void _menuOnHoverEnter(MouseEvent event) {
        HBox root = (HBox) event.getSource();

        if (root != _selectedMenu) {
            ObservableList<Node> rootChild = root.getChildren();

            ObservableList<Node> labelChild = ((HBox) rootChild.get(1)).getChildren();

            // Change icon fill color
            ((FontAwesomeIconView) labelChild.get(0)).setFill(Color.valueOf(_hoverColor));
            
            // Change label font color
            Label label = (Label) labelChild.get(1);
            label.setTextFill(Color.valueOf(_hoverColor));
            label.setFont(Font.font("System", FontWeight.NORMAL, 16));
        }
    }

    @FXML
    private void _menuOnHoverExit(MouseEvent event) {
        HBox root = (HBox) event.getSource();

        if (root != _selectedMenu) {
            ObservableList<Node> rootChild = root.getChildren();

            ObservableList<Node> labelChild = ((HBox) rootChild.get(1)).getChildren();

            // Change icon fill color
            ((FontAwesomeIconView) labelChild.get(0)).setFill(Color.valueOf(_unselectedLabelColor));
            
            // Change label font color
            Label label = (Label) labelChild.get(1);
            label.setTextFill(Color.valueOf(_unselectedLabelColor));
            label.setFont(Font.font("System", FontWeight.NORMAL, 16));
        }
    }

    @FXML
    private void _configOnHoverEnter(MouseEvent event) {
        config.setFill(Color.valueOf(_hoverColor));
    }

    @FXML
    private void _configOnHoverExit(MouseEvent event) {
        config.setFill(Color.valueOf(_unselectedLabelColor));
    }
}
