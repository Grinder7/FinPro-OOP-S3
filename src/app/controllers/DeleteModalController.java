package app.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import app.interfaces.DBActions;
import javafx.application.Platform;
import javafx.collections.ObservableList;
// Javafx lib
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DeleteModalController<T> implements Initializable {
    @FXML
    private Label title;

    private Stage _modalStage;
    private String _name;
    private ObservableList<T> _list;
    private int _idx;

    public void setStage(Stage stage) {_modalStage = stage;}

    public void setName(String name) {_name = name;}

    public void setList(ObservableList<T> list) {_list = list;}

    public void setIdx(int idx) {_idx = idx;}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            // Shorten name
            if (_name.length() > 5) {
                _name = _name.substring(0, 5) + "...";
            }

            title.setText(String.format("Delete \"%s\" Record ?", _name));
        });
    }

    @FXML
    private void _yesBtnHandler(ActionEvent event) {
        DBActions inter = (DBActions) _list.get(_idx);

        inter.delete();

        _list.remove(_idx);

        _modalStage.close();
    }

}
