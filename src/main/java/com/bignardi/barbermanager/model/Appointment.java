package com.bignardi.barbermanager.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private Client client;
    private LocalDateTime date;
    private String timeString;

    public Appointment() {
    }

    public Appointment(Client client, LocalDateTime date) {
        this.client = client;
        this.date = date;
        timeString = date.format(DateTimeFormatter.ofPattern("HH mm"));
    }
/*
    public Appointment(String name, LocalDateTime date) {
        this.name = name;
        this.date = date;
        stringHourMin = date.format(DateTimeFormatter.ofPattern("HH mm"));
        duration = 20;
    }*/

    public Appointment(Client client, int year, int month, int day, int hour, int min) throws IllegalArgumentException {
        this.client = client;
        date = LocalDateTime.of(year, month, day, hour, min);
        timeString = date.format(DateTimeFormatter.ofPattern("HH mm"));
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    @Override
    public String toString() {
        return date.format(DateTimeFormatter.ofPattern("HH:mm")) + " -> "
                + client.toString();
    }
    public String toStringFull(){
        return client.toString() + " " + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
