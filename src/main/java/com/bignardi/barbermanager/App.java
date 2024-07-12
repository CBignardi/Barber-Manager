package com.bignardi.barbermanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        //scene.getStylesheets().add(getClass().getResource("styles/jmetro/light_theme.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
