package app.views;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertBoxView {
    public static void showAlert(AlertType type, String title, String msg) {
        Alert alertBox = new Alert((AlertType) type);

        // Set alert box properties
        alertBox.setTitle(title);
        alertBox.setHeaderText(null);
        alertBox.setContentText(msg);

        alertBox.showAndWait();
    }
}
