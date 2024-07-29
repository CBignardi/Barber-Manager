package com.bignardi.barbermanager.controller;

import com.bignardi.barbermanager.model.Client;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

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
        if (name == null || duration == null) {
            throw new NullPointerException();
        }
        return new Client(name, Integer.parseInt(duration));
    }
}
