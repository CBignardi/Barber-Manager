package com.bignardi.barbermanager.controller;

import com.bignardi.barbermanager.model.Appointment;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;

public class RemoveAppointmentController {
    @FXML
    private ChoiceBox<String> choiceBoxName;
    private ArrayList<String> arrayClientsName;
    private Appointment appointment;

    @FXML
    public void initialize() {
        choiceBoxName.getItems().addAll(arrayClientsName);
    }

    public void setArrayClientsName(ArrayList<Appointment> appointments){
        for(Appointment appointment : appointments){
            arrayClientsName.add(appointment.getClient().getName());
        }
    }


    public Appointment getAppointment(){
        return appointment;
    }

}
