package app.views;

import app.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Window;

public class AlertBoxView<T> {
    public static void showAlert(AlertType type, String title, String msg) {
        Alert alertBox = new Alert((AlertType) type);

        // Set alert box properties
        alertBox.setTitle(title);
        alertBox.setHeaderText(null);
        alertBox.setContentText(msg);

        // Set windows owner
        alertBox.initOwner((Window) Main.getAppStage());

        alertBox.show();
    }
}
