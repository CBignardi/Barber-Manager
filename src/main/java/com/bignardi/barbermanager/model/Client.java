package com.bignardi.barbermanager.model;

import java.text.Format;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Client {
    private String name;
    private int duration;
    private LocalDateTime date;
    private String stringHourMin;

    public String getStringHourMin() {
        return stringHourMin;
    }

    public void setStringHourMin(String stringHourMin) {
        this.stringHourMin = stringHourMin;
    }

    public Client(){

    }

    public Client(String name, int duration, LocalDateTime date) {
        this.name = name;
        this.duration = duration;
        this.date = date;
        stringHourMin = date.format(DateTimeFormatter.ofPattern("HH mm"));
    }

    public Client(String name, LocalDateTime date) {
        this.name = name;
        this.date = date;
        stringHourMin = date.format(DateTimeFormatter.ofPattern("HH mm"));
        duration = 20;
    }

    public Client(String name, int year, int month, int day, int hour, int min) throws IllegalArgumentException {
        this.name = name;
        date = LocalDateTime.of(year, month, day, hour, min);
        stringHourMin = date.format(DateTimeFormatter.ofPattern("HH mm"));
        duration = 20;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        if (duration <= 0) {
            this.duration = 0;
        }
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                ", date=" + date.toString() +
                '}';
    }
}
