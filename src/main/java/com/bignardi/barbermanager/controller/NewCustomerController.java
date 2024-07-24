package com.bignardi.barbermanager.controller;

import com.bignardi.barbermanager.model.Appointment;
import com.bignardi.barbermanager.model.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class NewCustomerController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField durationField;
    private String name;
    private String duration;

    @FXML
    public void initialize() {
        nameField.textProperty().addListener((observable, oldValue, newValue) -> name = newValue);
        durationField.textProperty().addListener((observable, oldValue, newValue) -> duration = newValue);
    }

    public Client getUsualClient() throws NumberFormatException {
        if(name == null || duration == null){
            throw new NullPointerException();
        }
        return new Client(name, Integer.parseInt(duration));
    }

    void showWrongDurationAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alert");
        alert.setHeaderText("Wrong duration");
        alert.setContentText("Please insert a number in the duration field, you put something that isn't a number!");
        alert.showAndWait();
    }
}
