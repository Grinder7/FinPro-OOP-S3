package app.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.FutureTask;

import app.Main;
import app.views.AlertBoxView;
import database.DBConnection;

// Javafx lib
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Platform;
import javafx.concurrent.Task;

public class LoadingController implements Initializable {
    @FXML
    private ProgressBar progress_bar;
    @FXML
    private Label status_label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Task<Void> task = new Task<>() {
            @Override
            public Void call() throws Exception {
                updateProgress(6, 10); // 60% complete

                FutureTask<Void> showStatus = new FutureTask<>(() -> {
                    status_label.setText("Connecting to database server");

                    return null;
                });

                Platform.runLater(showStatus);

                try {
                    showStatus.get();
                } 
                catch (Exception e) {
                    e.printStackTrace();
                }

                DBConnection.init(); // Initialize database connection

                // Check if app can communicate with database server
                if (!DBConnection.isEstablished()) {
                    FutureTask<Void> interupt = new FutureTask<>(() -> {
                        AlertBoxView.showAlert(AlertType.ERROR, "Connection Error", 
                        "Couldn't communicate with database server. Make sure your credentials are correct and server is online");

                        return null;
                    });

                    Platform.runLater(interupt);

                    try {
                        interupt.get();
                    } 
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                updateProgress(8, 10); // 80% complete

                Thread.sleep(250);

                updateProgress(10, 10); // 100% complete

                Thread.sleep(700);

                return null;
            }
        };

        task.setOnSucceeded(arg -> {
            // Redirect to home page
            try {
                Main.showPage("./views/homeView.fxml", true);
            }
            catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        });

        // Bind task progress with progress bar
        progress_bar.progressProperty().bind(task.progressProperty());
        
        new Thread(task).start(); // Start thread
    }
}
