package com.bignardi.barbermanager.controller;

import com.bignardi.barbermanager.model.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

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
    private LocalDate date;
    public LocalDateTime localDateTime;


    public void getName(ActionEvent event) {
        name = nameField.getText();
        System.out.println("name: " + name);
    }

    public void getDuration(ActionEvent event) {
        duration = Integer.parseInt(durationField.getText());
        System.out.println("duration: " + duration);
    }

    public void getTimeAppointment(ActionEvent event) {
        timeAppointment = timeField.getText();
        System.out.println("time appointment: " + timeAppointment);
    }

    public void getDate(ActionEvent event){
        date = datePicker.getValue();
        System.out.println("date: " + date.toString());
    }

    public Client getClient() {
        LocalTime localTime = LocalTime.parse(timeAppointment, DateTimeFormatter.ofPattern("HH mm"));
        localDateTime = LocalDateTime.of(date, localTime);
        return new Client(name, localDateTime);
    }

    //@FXML
    //public void initialize() {
    //  nameField.textProperty().addListener((observable, oldValue, newValue) -> name = newValue);
    //durationField.textProperty().addListener((observable, oldValue, newValue) -> duration = Integer.parseInt(newValue));
    //timeField.textProperty().addListener((observable, oldValue, newValue) -> timeAppointment = newValue);
    //datePicker.converterProperty().addListener((observable, oldValue, newValue) -> date = newValue.toString());
    //}

    void update() {
        nameField.textProperty().set(name);
        durationField.textProperty().set(duration + "");
        timeField.textProperty().set(timeAppointment);
        //datePicker.converterProperty().set();
    }


}
