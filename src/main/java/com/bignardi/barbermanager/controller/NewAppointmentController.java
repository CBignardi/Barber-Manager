package com.bignardi.barbermanager.controller;

import com.bignardi.barbermanager.model.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
    public int duration;
    public String timeAppointment;
    private StringConverter<LocalDate> dateConverted;
    private LocalDate date;
    public LocalDateTime localDateTime;

    public Client getClient() {
        LocalTime localTime = LocalTime.parse(timeAppointment, DateTimeFormatter.ofPattern("HH mm"));
        localDateTime = LocalDateTime.of(date, localTime);
        return new Client(name, duration, localDateTime);
    }

    @FXML
    public void initialize() {
        nameField.textProperty().addListener((observable, oldValue, newValue) -> name = newValue);
        durationField.textProperty().addListener((observable, oldValue, newValue) -> duration = Integer.parseInt(newValue));
        timeField.textProperty().addListener((observable, oldValue, newValue) -> timeAppointment = newValue);
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> date = newValue);
    }
/*
    void update() {
        nameField.textProperty().set(name);
        durationField.textProperty().set(duration + "");
        timeField.textProperty().set(timeAppointment);
        datePicker.converterProperty().set(dateConverted);
    }*/


}
