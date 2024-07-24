package com.bignardi.barbermanager.controller;

import com.bignardi.barbermanager.model.Appointment;
import com.bignardi.barbermanager.model.Client;
import javafx.event.Event;
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
    private ArrayList<String> arrayClientsName = new ArrayList<>();
    private ArrayList<Client> arrayClients;

    public Appointment getAppointment() {
        LocalTime localTime = LocalTime.parse(timeAppointment, DateTimeFormatter.ofPattern("HH mm"));
        localDateTime = LocalDateTime.of(date, localTime);
        Client client = new Client(name, duration);
        return new Appointment(client, localDateTime);
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

    private void initializeChoiceBox() {
        try {
            choiceBox.getItems().addAll(arrayClientsName.toArray());
            choiceBox.setOnAction(this::setClientParam);
        } catch (NullPointerException ignored) {
        }
    }

    @FXML
    public void initialize() {
        nameField.textProperty().addListener((observable, oldValue, newValue) -> name = newValue);
        durationField.textProperty().addListener((observable, oldValue, newValue) -> duration = Integer.parseInt(newValue));
        timeField.textProperty().addListener((observable, oldValue, newValue) -> timeAppointment = newValue);
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> date = newValue);
    }

    private Client findClient(String name) {
        for (Client client : arrayClients) {
            if (client.getName().equals(name)) {
                return client;
            }
        }
        return null;
    }

    private void setClientParam(Event event) {
        Client client = findClient((String) choiceBox.getValue());
        nameField.textProperty().set(client.getName());
        durationField.textProperty().set(client.getDuration() + "");
    }


    /* fatto con altra verisone di set, poi cancellare
    private ArrayList<String> getArrayUsualClientName() throws NullPointerException {
        ArrayList<Appointment> arrayAppointments = OverviewController.getArrayUsualClient();
        if (!arrayAppointments.isEmpty()) {
            for (Appointment appointment1 : arrayAppointments) {
                System.out.println(appointment1.toString());
                arrayName.add(appointment1.getName());
            }
        }

        System.out.println(arrayName.toString());
        return arrayName;
    }*/


}
