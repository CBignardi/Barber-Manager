package com.bignardi.barbermanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("overviewpage-view.fxml")));
        Scene scene = new Scene(view);
        stage.setTitle("Barber Manager");
        stage.getIcons().add(new Image((Objects.requireNonNull(getClass().getResourceAsStream("icons/Barber icon.png")))));
        stage.setScene(scene);

        stage.show();
    }
}
