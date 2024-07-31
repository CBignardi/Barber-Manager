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
    private boolean isRemoveCustomer = false;

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
        arrayClients.remove(selectedClient);
        arrayClients.add(new Client(name, Integer.parseInt(duration)));
        arrayClients.sort(Comparator.comparing(Client::getName));
        return arrayClients;
    }

    @FXML
    public void removeCustomer(ActionEvent event) {
        System.out.println("remove customer");
        if (selectedClient != null) {
            isRemoveCustomer = true;
            clearClientRemoved();
        } else {
            isRemoveCustomer = false;
        }
    }

    private void clearClientRemoved() {
        System.out.println("clear client removed");
        arrayClients.remove(selectedClient);
        choiceBox.setValue(new Client());
        updateChoiceBox();
    }

    public Client getRemovedCustomer() {
        System.out.println("get removed customer");
        if (isRemoveCustomer) {
            return selectedClient;
        } else {
            return null;
        }
    }

}
