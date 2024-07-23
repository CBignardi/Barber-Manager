package com.bignardi.barbermanager.controller;

import com.bignardi.barbermanager.model.Client;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NewCustomerController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField durationField;
    private Client usualClient = new Client();

    @FXML
    public void initialize() {
        nameField.textProperty().addListener((observable, oldValue, newValue) -> usualClient.setName(newValue));
        durationField.textProperty().addListener((observable, oldValue, newValue) -> usualClient.setDuration(Integer.parseInt(newValue)));
    }


    void update() {
        nameField.textProperty().set(usualClient.getName());
        durationField.textProperty().set(usualClient.getDuration() + "");
    }


    public Client getUsualClient() {
        return usualClient;
    }
}
