package com.bignardi.barbermanager.controller;

import com.bignardi.barbermanager.model.Appointment;
import com.bignardi.barbermanager.model.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.DateTimeException;
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
    @FXML
    private ToggleButton toggleButton;

    private String name;
    private String duration;
    private String timeAppointment;
    private LocalDate date;
    private final ObservableList<String> arrayClientsName = FXCollections.observableArrayList();
    private ArrayList<Client> arrayClients = new ArrayList<>();

    public Appointment getAppointment() throws DateTimeException, NumberFormatException {
        System.out.println("prima");
        if (timeAppointment == null || date == null || name == null || duration == null) {
            throw new NullPointerException();
        }
        LocalTime localTime = LocalTime.parse(timeAppointment, DateTimeFormatter.ofPattern("HH mm"));
        System.out.println("done localtime");
        LocalDateTime localDateTime = LocalDateTime.of(date, localTime);
        System.out.println("done localtimedate");
        Client client = new Client(name, Integer.parseInt(duration));

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
            choiceBox.setItems(arrayClientsName);
            choiceBox.setOnAction(this::setClientParam);
        } catch (NullPointerException ignored) {
        }
    }

    @FXML
    public void initialize() {
        nameField.textProperty().addListener((observable, oldValue, newValue) -> name = newValue);
        durationField.textProperty().addListener((observable, oldValue, newValue) -> duration = newValue);
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

    public boolean getIsUsualCustomerSelected(){
        return toggleButton.isSelected();
    }

    @FXML
    public void handleChangeToggleText(){
        if(toggleButton.isSelected()){
            toggleButton.setText("Do");
        }else{
            toggleButton.setText("Do not");
        }
    }
}
