package com.bignardi.barbermanager.controller;

import com.bignardi.barbermanager.model.Client;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;

public class NewAppointmentController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField durationField;
    @FXML
    private TextField timeField;
    @FXML
    private DatePicker datePicker;

    Client client;

    private String name;
    private int duration;
    private String timeAppointment;
    private String date;

    @FXML
    public void initialize(){
        nameField.textProperty().addListener((observable, oldValue, newValue) -> name = newValue);
        durationField.textProperty().addListener((observable, oldValue, newValue) -> duration = Integer.parseInt(newValue));
        timeField.textProperty().addListener((observable, oldValue, newValue) -> timeAppointment = newValue);
        datePicker.converterProperty().addListener((observable, oldValue, newValue) -> date = newValue.toString());
    }

    void update() {
        nameField.textProperty().set(name);
        durationField.textProperty().set(duration + "");
        timeField.textProperty().set(timeAppointment);
        datePicker.converterProperty().set();
    }



}
