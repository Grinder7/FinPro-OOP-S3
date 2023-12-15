package app.controllers;

import java.util.ResourceBundle;
import java.net.URL;

// Model(s)
import app.models.Item;

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
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;

public class SupplyListController implements Initializable {
    // Layout fxid(s)
    @FXML
    private TableView<Item> table;

    // Column fxid(s)
    @FXML
    private TableColumn<Integer, Void> num_col;
    @FXML
    private TableColumn<Item, String> name_col;
    @FXML
    private TableColumn<Item, Integer> quantity_col;
    @FXML
    private TableColumn<HBox, Button> actions_col;

    // Input field fxid(s)
    @FXML
    private TextField search_field;

    private static ObservableList<Item> _list;

    public SupplyListController() {
        new Thread(new Runnable() {
            public void run() {
                // Pooling database
                while (true) {
                    try {
                        // Wait for 5 sec
                        Thread.sleep(5000);

                        _initTableContent();
                        
                        System.out.println("Polling CareTaker");
                    } 
                    catch (InterruptedException e) {
                        System.err.println("DBPollingThread interrupted");
                        break;
                    }
                }
            }
        }) {{
            setName("DBPollingThread");
            start();
        }};
    }

    public static ObservableList<Item> getList() {return _list;}

    private void _initTableContent() {
        _list = Item.fetch();
        table.setItems(_list);
    }

    private void _showModal(String action, int idx) {
        Stage newStage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass()
            .getResource("../views/supplymodalStackView.fxml"));

        try {
            Parent root = loader.load();

            SupplyModalController controller = loader.getController();

            controller.setStage(newStage);
            controller.setAction(action);
            controller.setIdx(idx);

            newStage.setScene(new Scene(root));

            newStage.centerOnScreen();

            newStage.setResizable(false);

            newStage.setTitle(action.equals("insert") ? "Add New Data" : "Update Data");

            newStage.initModality(Modality.APPLICATION_MODAL);

            newStage.showAndWait();

            _initTableContent();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void _showDeleteModal(int cellIdx) {
        Stage newStage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass()
            .getResource("../views/deletemodalStackView.fxml"));
        
        try {
            Parent root = loader.load();
        
            DeleteModalController<Item> controller = loader.getController();

            controller.setStage(newStage);
            controller.setName(_list.get(cellIdx).getName());
            controller.setList(_list);
            controller.setIdx(cellIdx);

            newStage.setScene(new Scene(root));

            newStage.centerOnScreen();

            newStage.setResizable(false);

            newStage.setTitle("Delete Record");

            newStage.initModality(Modality.APPLICATION_MODAL);

            newStage.showAndWait();

            _initTableContent();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize cols data type
        num_col.setCellFactory(col -> {
            TableCell<Integer, Void> cell = new TableCell<>();

            cell.textProperty().bind(Bindings.createStringBinding(() -> {
                if (cell.isEmpty()) {return null;}
                else {
                    return Integer.toString(cell.getIndex() + 1);
                }
            }, cell.emptyProperty(), cell.indexProperty()));

            return cell;
        });
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantity_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        actions_col.setCellFactory(col -> {
            TableCell<HBox, Button> cell = new TableCell<>();

            cell.graphicProperty().bind(Bindings.createObjectBinding(() -> {
                HBox hbox = new HBox() {{
                    setAlignment(Pos.CENTER);
                }};

                // Delete button
                Button delBtn = new Button() {{
                    setStyle("-fx-background-color: transparent;");
                }};

                // Set button attribute(s)
                Text delGlyph = FontAwesomeIconFactory.get()
                    .createIcon(FontAwesomeIcon.valueOf("TRASH"), "1.2em");
                delGlyph.setFill(Paint.valueOf("RED"));

                delBtn.setGraphic(delGlyph);
                delBtn.setCursor(Cursor.HAND);

                // Set button pressed handler
                delBtn.setOnAction(e -> {_showDeleteModal(cell.getIndex());});
                
                // Edit button
                Button editBtn = new Button() {{
                    setStyle("-fx-background-color: transparent;");
                }};

                // Set button attribute(s)
                Text editGlyph = FontAwesomeIconFactory.get()
                    .createIcon(FontAwesomeIcon.valueOf("PENCIL"), "1.2em");
                editGlyph.setFill(Paint.valueOf("BLACK"));

                editBtn.setGraphic(editGlyph);
                editBtn.setCursor(Cursor.HAND);

                // Set button pressed handler
                editBtn.setOnAction(e -> {
                    _showModal("update", cell.getIndex());
                });

                // Add buttons into hbox
                hbox.getChildren().addAll(delBtn, editBtn);

                if (cell.isEmpty()) {return null;}
                else {return hbox;}
            }, cell.emptyProperty(), cell.indexProperty()));

            return cell;
        });

        _initTableContent();
    }

    @FXML
    private void _addBtnHandler(MouseEvent event) {
        _showModal("insert", -1);
    }

    @FXML
    private void _searchBtnHandler(MouseEvent event) {
        String searchName = search_field.getText().trim()
            .replaceAll("\\s{2,}", " ");
        
        _list = Item.search(searchName);

        table.setItems(_list);
    }

}
