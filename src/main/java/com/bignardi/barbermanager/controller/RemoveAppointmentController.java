package com.bignardi.barbermanager.controller;

import com.bignardi.barbermanager.model.Client;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;

public class RemoveAppointmentController {
    @FXML
    private ChoiceBox<String> choiceBoxName;
    private ArrayList<String> arrayName;
    private Client client;

    @FXML
    public void initialize() {
        arrayName = getArrayUsualClientName();
        choiceBoxName.getItems().addAll(arrayName);

    }

    private ArrayList<String> getArrayUsualClientName(){
        OverviewController c = new OverviewController();
        for(Client client1 : c.getArrayUsualClient()){
            arrayName.add(client1.getName());
        }
        System.out.println(arrayName.toString());
        return arrayName;
    }

    public Client getClient(){
        return client;
    }

}
