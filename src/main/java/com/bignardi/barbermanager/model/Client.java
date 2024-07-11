package com.bignardi.barbermanager.model;


import javafx.collections.ObservableList;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Client {
    private String name;
    private int duration;
    private LocalDate date;
    private String stringHourMin;

    public String getStringHourMin() {
        return stringHourMin;
    }

    public void setStringHourMin(String stringHourMin) {
        this.stringHourMin = stringHourMin;
    }

    public Client(String name, int averageTime, LocalDate date) {
        this.name = name;
        this.duration = duration;
        this.date = date;
        stringHourMin = "c";
    }

    public Client(String name, LocalDate date) {
        this.name = name;
        this.date = date;
        stringHourMin = date.toString();
        averageTime = 20;
    }

    public Client(String name, int year, int month, int day, int hour, int min) throws IllegalArgumentException {
        this.name = name;
        //date = new LocalDate.atTime(year, month, day, hour, min);
        stringHourMin = date.toString();
        averageTime = 20;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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
