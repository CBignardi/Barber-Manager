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

    public Client(String name, int averageTime, LocalDateTime date) {
        this.name = name;
        this.duration = duration;
        this.date = date;
        stringHourMin = date.format(DateTimeFormatter.ofPattern("HH mm"));
    }

    public Client(String name, LocalDateTime date) {
        this.name = name;
        this.date = date;
        stringHourMin = date.toString();
        duration = 20;
    }

    public Client(String name, int year, int month, int day, int hour, int min) throws IllegalArgumentException {
        this.name = name;
        stringHourMin = "a";
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

    public void setAverageTime(int duration) throws IllegalArgumentException {
        if (duration <= 0) {
            throw new IllegalArgumentException();
        }
        this.duration = duration;
    }
}
