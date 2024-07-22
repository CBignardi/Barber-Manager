package com.bignardi.barbermanager.controller;

import com.bignardi.barbermanager.model.Client;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NewCustomerController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField averageTimeField;
    Client usualClient;

    @FXML
    public void initialize() {
        nameField.textProperty().addListener((observable, oldValue, newValue) -> usualClient.setName(newValue));
        averageTimeField.textProperty().addListener((observable, oldValue, newValue) -> usualClient.setAverageTime(Integer.parseInt(newValue)));
    }


    void update() {
        nameField.textProperty().set(usualClient.getName());
        averageTimeField.textProperty().set(usualClient.getDuration() + "");
    }


    public Client getUsualClient() {
        return usualClient;
    }

    public void setClient(Client client) {
        this.usualClient = client;
        update();
    }
}
