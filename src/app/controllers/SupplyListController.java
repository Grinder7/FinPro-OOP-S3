package app.controllers;

// Javafx lib
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class SupplyListController {
    // Layout fxid(s)
    @FXML
    private TableView<?> table;

    // Column fxid(s)
    @FXML
    private TableColumn<Integer, Void> num_col;
    @FXML
    private TableColumn<?, String> name_col;
    @FXML
    private TableColumn<?, Integer> quantity_col;
    @FXML
    private TableColumn<HBox, Void> actions_col;

    // Input field fxid(s)
    @FXML
    private TextField search_field;

    @FXML
    void _addBtnHandler(MouseEvent event) {

    }

    @FXML
    void _searchBtnHandler(MouseEvent event) {

    }

}
