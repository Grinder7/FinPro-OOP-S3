package app.controllers;

import java.net.URL;
import java.util.ResourceBundle;

// Model(s)
import app.models.Caretaker;

// Javafx lib
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
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

    private static ObservableList<Caretaker> _list;

    public static ObservableList<Caretaker> getList() {return _list;}
    private void _refreshTable() {
        _list = Caretaker.fetch();

        table.refresh();
    }

    @Override
    public void initialize(URL location, ResourceBundle recourses) {
        _list = Caretaker.fetch();

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

                // Delete button
                Button delBtn = new Button();
                delBtn.setStyle("-fx-background-color: transparent;");

                Text delGlyph = FontAwesomeIconFactory.get()
                    .createIcon(FontAwesomeIcon.valueOf("TRASH"), "1.2em");
                delGlyph.setFill(Paint.valueOf("RED"));

                delBtn.setGraphic(delGlyph);

                delBtn.setCursor(Cursor.HAND);
                
                // Edit button
                Button editBtn = new Button();
                editBtn.setStyle("-fx-background-color: transparent;");

                Text editGlyph = FontAwesomeIconFactory.get()
                    .createIcon(FontAwesomeIcon.valueOf("PENCIL"), "1.2em");
                editGlyph.setFill(Paint.valueOf("BLACK"));

                editBtn.setGraphic(delBtn);

                editBtn.setCursor(Cursor.HAND);

                // Add buttons into hbox
                hbox.getChildren().addAll(editGlyph, editBtn);

                if (cell.isEmpty()) {return null;}
                else {return hbox;}
            }, cell.emptyProperty(), cell.indexProperty()));

            return cell;
        });

        table.setItems(_list);
    }

    @FXML
    private void _addBtnHandler(MouseEvent event) throws Exception {
        Stage newStage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/caretakermodalStackView.fxml"));

        Parent root = loader.load();

        CaretakerModalController controller = loader.getController();

        controller.setStage(newStage);
        controller.setAction("insert");

        newStage.setScene(new Scene(root));

        newStage.centerOnScreen();

        newStage.setMinWidth(newStage.getWidth());
        newStage.setMinHeight(newStage.getHeight());

        newStage.setResizable(false);

        newStage.setTitle("New Caretaker");

        newStage.initModality(Modality.APPLICATION_MODAL);

        newStage.showAndWait();

        _refreshTable();
    }

    @FXML
    private void _searchBtnHandler(MouseEvent event) {

    }

}
