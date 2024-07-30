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

public class EditCustomerController {
    @FXML
    ChoiceBox<String> choiceBox;
    @FXML
    TextField nameField;
    @FXML
    TextField durationField;
    private String name;
    private String duration;
    private Client selectedClient;
    private final ObservableList<String> arrayClientsName = FXCollections.observableArrayList();
    private ArrayList<Client> arrayClients = new ArrayList<>();
    private boolean isRemoveCustomer = false;

    @FXML
    public void initialize() {
        nameField.textProperty().addListener((observable, oldValue, newValue) -> name = newValue);
        durationField.textProperty().addListener((observable, oldValue, newValue) -> duration = newValue);
    }

    private void initializeChoiceBox() {
        try {
            choiceBox.setItems(arrayClientsName);
            choiceBox.setOnAction(this::setClientParam);
        } catch (NullPointerException ignored) {
        }
    }

    private void setClientParam(Event event){
        selectedClient = findClient((String) choiceBox.getValue());
        nameField.textProperty().set(selectedClient.getName());
        durationField.textProperty().set(selectedClient.getDuration() + "");
    }

    private Client findClient(String name) {
        for (Client client : arrayClients) {
            if (client.getName().equals(name)) {
                return client;
            }
        }
        return null;
    }

    public void setArrayClientsName(ArrayList<Client> clients) {
        if (!clients.isEmpty()) {
            arrayClients = clients;
            for (Client client : clients) {
                arrayClientsName.add(client.getName());
            }
        }
        initializeChoiceBox();
    }

    public Client[] getEditedClient(){
        Client[] clients = new Client[2];
        clients[0] = selectedClient;
        clients[1] = new Client(name, Integer.parseInt(duration));
        return clients;
    }

    @FXML
    public void removeCustomer(ActionEvent event){
        if(selectedClient != null){
            isRemoveCustomer = true;
        }else{
            isRemoveCustomer = false;
        }
    }

    public Client getRemovedCustomer(){
        if(isRemoveCustomer){
            return selectedClient;
        }else{
            return null;
        }
    }

}
