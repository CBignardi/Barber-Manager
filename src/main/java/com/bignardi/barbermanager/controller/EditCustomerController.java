package com.bignardi.barbermanager.controller;

import com.bignardi.barbermanager.model.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Comparator;

public class EditCustomerController {
    @FXML
    ChoiceBox<Client> choiceBox;
    @FXML
    TextField nameField;
    @FXML
    TextField durationField;
    private String name;
    private String duration;
    private Client selectedClient;
    private ObservableList<Client> arrayClients = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        nameField.textProperty().addListener((observable, oldValue, newValue) -> name = newValue);
        durationField.textProperty().addListener((observable, oldValue, newValue) -> duration = newValue);
    }

    private void initializeChoiceBox() {
        try {
            updateChoiceBox();
            choiceBox.setOnAction(this::setClientParam);
        } catch (NullPointerException ignored) {
        }
    }

    private void updateChoiceBox() {
        choiceBox.setItems(arrayClients);
    }

    private void setClientParam(Event event) {
        try {
            selectedClient = choiceBox.getValue();
            nameField.textProperty().set(selectedClient.getName());
            durationField.textProperty().set(selectedClient.getDuration() + "");
        } catch (NullPointerException ignored) {
        }
    }

    public void setArrayClientsName(ObservableList<Client> clients) {
        if (!clients.isEmpty()) {
            arrayClients = clients;
        }
        initializeChoiceBox();
    }

    public ArrayList<Client> getArrayClient() {
        int index = getClientIndex(selectedClient);
        arrayClients.get(index).setName(name);
        arrayClients.get(index).setDuration(Integer.parseInt(duration));
        arrayClients.sort(Comparator.comparing(Client::getName));
        return new ArrayList<>(arrayClients);
    }

    private int getClientIndex(Client client) {
        for (int i = 0; i < arrayClients.size(); i++) {
            if (arrayClients.get(i).equals(client)) {
                return i;
            }
        }
        return arrayClients.size() + 1;
    }

    @FXML
    public void removeCustomer(ActionEvent event) {
        if (selectedClient != null) {
            clearClientRemoved();
        }
    }

    private void clearClientRemoved() {
        arrayClients.remove(selectedClient);
        choiceBox.setValue(new Client());
        updateChoiceBox();
    }
}
