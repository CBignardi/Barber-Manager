package com.bignardi.barbermanager.controller;

import com.bignardi.barbermanager.model.Client;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NewAppointmentController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField durationField;
    @FXML
    private TextField timeField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ChoiceBox choiceBox;

    private String name;
    private int duration;
    private String timeAppointment;
    private LocalDate date;
    private LocalDateTime localDateTime;
    private ArrayList<String> arrayName;

    public Client getClient() {
        LocalTime localTime = LocalTime.parse(timeAppointment, DateTimeFormatter.ofPattern("HH mm"));
        localDateTime = LocalDateTime.of(date, localTime);
        return new Client(name, duration, localDateTime);
    }

    public void setArrayName(ArrayList<Client> clients){
        for(Client client : clients){
            arrayName.add(client.getName());
        }
    }


    @FXML
    public void initialize() {
        nameField.textProperty().addListener((observable, oldValue, newValue) -> name = newValue);
        durationField.textProperty().addListener((observable, oldValue, newValue) -> duration = Integer.parseInt(newValue));
        timeField.textProperty().addListener((observable, oldValue, newValue) -> timeAppointment = newValue);
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> date = newValue);
        try {
            choiceBox.getItems().addAll(arrayName);
        }catch (NullPointerException ignored){
        }

        /*try {
            arrayName = getArrayUsualClientName();
            arrayName.addAll(choiceBox.getItems());
        } catch (NullPointerException ignored) {
        }*/
    }


    private ArrayList<String> getArrayUsualClientName() throws NullPointerException {
        ArrayList<Client> arrayClients = OverviewController.getArrayUsualClient();
        if (!arrayClients.isEmpty()) {
            for (Client client1 : arrayClients) {
                System.out.println(client1.toString());
                arrayName.add(client1.getName());
            }
        }

        System.out.println(arrayName.toString());
        return arrayName;
    }


    /*
    void update() {
        nameField.textProperty().set(name);
        durationField.textProperty().set(duration + "");
        timeField.textProperty().set(timeAppointment);
        datePicker.converterProperty().set(dateConverted);
    }*/


}
