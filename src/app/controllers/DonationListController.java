package app.controllers;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

// Model(s)
import app.models.Donation;

// Javafx lib
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

public class DonationListController implements Initializable {
    // Layout fxid(s)
    @FXML
    private TableView<Donation> table;

    // Column fxid(s)
    @FXML
    private TableColumn<Integer, Void> num_col;
    @FXML
    private TableColumn<Donation, String> donator_col;
    @FXML
    private TableColumn<Donation, String> item_col;
    @FXML
    private TableColumn<Donation, Integer> quantity_col;
    @FXML
    private TableColumn<Donation, Date> date_col;

    // Input field fxid(s)
    @FXML
    private TextField search_field;

    private static ObservableList<Donation> _list;

    public DonationListController() {
        new Thread(new Runnable() {
            public void run() {
                // Polling database
                while (true) {
                    try {
                        // Wait for 5 sec
                        Thread.sleep(5000);

                        _initTableContent();
                        
                        System.out.println("Polling Donation");
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

    public static ObservableList<Donation> getList() {return _list;}

    private void _initTableContent() {
        _list = Donation.fetch();
        table.setItems(_list);
    }

    private void _showModal() {
        Stage newStage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass()
            .getResource("../views/donationmodalStackView.fxml"));

        try {
            Parent root = loader.load();

            DonationModalController controller = loader.getController();

            controller.setStage(newStage);

            newStage.setScene(new Scene(root));

            newStage.centerOnScreen();

            newStage.setResizable(false);

            newStage.setTitle("Add New Data");
            
            newStage.getIcons().add(new Image(getClass().getResourceAsStream("../../assets/ico.png")));

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
        // Initialize table listener(s)
        table.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> source, Number oldWidth, Number newWidth) {
                TableHeaderRow header = (TableHeaderRow) table.lookup("TableHeaderRow");

                // Listener to resize table column using mouse
                header.reorderingProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        header.setReordering(false);
                    }
                });
                
                // Listener to resize table column using mouse
                header.addEventFilter(MouseEvent.MOUSE_DRAGGED, MouseEvent::consume);
            }
        });

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
        donator_col.setCellValueFactory(new PropertyValueFactory<>("donatorName"));
        item_col.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        quantity_col.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));
        date_col.setCellValueFactory(new PropertyValueFactory<>("date"));

        _initTableContent();
    }

    @FXML
    private void _addBtnHandler(MouseEvent event) {
        _showModal();
    }

    @FXML
    private void _searchBtnHandler(MouseEvent event) {
        String searchName = search_field.getText().trim()
        .replaceAll("\\s{2,}", " ");
    
        _list = Donation.search(searchName);

        table.setItems(_list);
    }
}
