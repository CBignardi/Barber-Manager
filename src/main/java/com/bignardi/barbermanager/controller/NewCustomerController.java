package com.bignardi.barbermanager.controller;

import com.bignardi.barbermanager.model.Client;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NewCustomerController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField averageTimeField;
    Client client;

    @FXML
    public void initialize() {
        nameField.textProperty().addListener((observable, oldValue, newValue) -> client.setName(newValue));
        averageTimeField.textProperty().addListener((observable, oldValue, newValue) -> client.setAverageTime(Integer.parseInt(newValue)));
    }

    void update() {
        nameField.textProperty().set(client.getName());
        averageTimeField.textProperty().set(client.getDuration() + "");
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
        update();
    }
}
