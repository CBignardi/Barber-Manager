package com.bignardi.barbermanager.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private Client client;
    private LocalDateTime date;

    public Appointment() {
    }

    public Appointment(Client client, LocalDateTime date) {
        this.client = client;
        this.date = date;
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

    public String toStringFull() {
        return client.toString() + " " + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
