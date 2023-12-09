package app.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import app.models.Caretaker;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
// Javafx lib
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import de.jensd.fx.glyphs.fontawesome.*;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;

class TableRow {
    public int num;
    public static Object content;

    public TableRow(int num, Object obj) {
        this.num = num;
        content = obj;
    }
}

public class CaretakerListController implements Initializable {
    // Input field fxid(s)
    @FXML
    private TextField search_field;

    // Layour fxid(s)
    @FXML
    private TableView<Caretaker> table;
    // Column fxid(s)
    @FXML
    private TableColumn<Caretaker, Void> num_col;
    @FXML
    private TableColumn<Caretaker, String> name_col;
    @FXML
    private TableColumn<Caretaker, String> phone_num_col;
    @FXML
    private TableColumn<Caretaker, Integer> age_col;
    @FXML
    private TableColumn<Caretaker, String> gender_col;
    @FXML
    private TableColumn<HBox, Void> actions_col;

    private ObservableList<Caretaker> _list;

    @Override
    public void initialize(URL location, ResourceBundle recourses) {
        // Initialize cols data type
        num_col.setCellFactory(col -> {
            TableCell<Caretaker, Void> cell = new TableCell<>();

            cell.textProperty().bind(Bindings.createStringBinding(() -> {
                if (cell.isEmpty()) {return null;}
                else {
                    return Integer.toString(cell.getIndex() + 1);
                }
            }, cell.emptyProperty(), cell.indexProperty()));

            return cell;
        });
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        phone_num_col.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        age_col.setCellValueFactory(new PropertyValueFactory<>("age"));
        gender_col.setCellValueFactory(new PropertyValueFactory<>("gender"));
        actions_col.setCellFactory(col -> {
            TableCell<HBox, Void> cell = new TableCell<>();

            cell.graphicProperty().bind(Bindings.createObjectBinding(() -> {
                HBox hbox = new HBox();
                hbox.setAlignment(Pos.CENTER);

                Button deleteBtn = new Button();
                deleteBtn.setStyle("-fx-background-color: transparent;");
                deleteBtn.setGraphic(FontAwesomeIconFactory.get()
                    .createIcon(FontAwesomeIcon.valueOf("TRASH"), "1.2em"));
                deleteBtn.setCursor(Cursor.HAND);
                
                Button editBtn = new Button();
                editBtn.setStyle("-fx-background-color: transparent;");
                Text glyph = FontAwesomeIconFactory.get()
                    .createIcon(FontAwesomeIcon.valueOf("EDIT"), "1.2em");
                glyph.setFill(Paint.valueOf("RED"));
                editBtn.setGraphic(glyph);
                editBtn.setCursor(Cursor.HAND);

                hbox.getChildren().addAll(deleteBtn, editBtn);

                if (cell.isEmpty()) {return null;}
                else {return hbox;}
            }, cell.emptyProperty(), cell.indexProperty()));

            return cell;
        });

        _list = HomeController.getCaretakers();

        table.setItems(_list);
    }

    @FXML
    private void _addBtnHandler(MouseEvent event) {

    }

    @FXML
    private void _searchBtnHandler(MouseEvent event) {

    }

}
