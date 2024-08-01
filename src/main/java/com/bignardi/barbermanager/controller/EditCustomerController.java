package com.bignardi.barbermanager.controller;

import com.bignardi.barbermanager.model.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.Comparator;
import java.util.concurrent.ConcurrentLinkedDeque;

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
        System.out.println("initialize");
        nameField.textProperty().addListener((observable, oldValue, newValue) -> name = newValue);
        durationField.textProperty().addListener((observable, oldValue, newValue) -> duration = newValue);
    }

    private void initializeChoiceBox() {
        System.out.println("initialize choice box");
        try {
            updateChoiceBox();
            choiceBox.setOnAction(this::setClientParam);
        } catch (NullPointerException ignored) {
        }
    }

    private void updateChoiceBox() {
        System.out.println("update choice");
        choiceBox.setItems(arrayClients);
    }

    private void setClientParam(Event event) {
        System.out.println("set client param");
        try {
            selectedClient = choiceBox.getValue();
            nameField.textProperty().set(selectedClient.getName());
            durationField.textProperty().set(selectedClient.getDuration() + "");
        } catch (NullPointerException ignored) {
        }
    }

    private Client findClient(String name) {
        System.out.println("find client");
        for (Client client : arrayClients) {
            if (client.getName().equals(name)) {
                return client;
            }
        }
        return new Client();
    }

    public void setArrayClientsName(ObservableList<Client> clients) {
        System.out.println("set Arrayclient name");
        if (!clients.isEmpty()) {
            arrayClients = clients;
        }
        initializeChoiceBox();
    }

    public ObservableList<Client> getArrayClient() {
        int index = getClientIndex(selectedClient);
        arrayClients.get(index).setName(name);
        arrayClients.get(index).setName(duration);
        arrayClients.sort(Comparator.comparing(Client::getName));
        return arrayClients;
    }

    private int getClientIndex(Client client) {
        for (int i = 0; i < arrayClients.size(); i++) {
            if (arrayClients.get(i).equals(client)) {
                return i;
            }
        }
        return 0;
    }

    @FXML
    public void removeCustomer(ActionEvent event) {
        System.out.println("remove customer");
        if (selectedClient != null) {
            clearClientRemoved();
        }
    }

    private void clearClientRemoved() {
        System.out.println("clear client removed");
        arrayClients.remove(selectedClient);
        choiceBox.setValue(new Client());
        updateChoiceBox();
    }


}
