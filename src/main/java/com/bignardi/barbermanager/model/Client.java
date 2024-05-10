package com.bignardi.barbermanager.model;


import javafx.util.StringConverter;

public class Client {
    private String name;
    private int averageTime;
    private DayDate date;
    private int min;
    private int hour;
    private String stringHourMin;


    public Client(String name, int averageTime, DayDate date) {
        this.name = name;
        this.averageTime = averageTime;
        this.date = date;
        stringHourMin = date.getHour() + ":" + date.getMinute();

    }

    public Client(String name, DayDate date) {
        this.name = name;
        this.date = date;
        stringHourMin = date.getHour() + ":" + date.getMinute();
        averageTime = 20;
    }

    public Client(String name, int year, int month, int day, int hour, int min) {
        this.name = name;
        date = new DayDate(year, month, day, hour, min);
        stringHourMin = hour + "";
        this.hour = hour;
        averageTime = 20;
        this.min = min;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(int averageTime) throws IllegalArgumentException {
        if (averageTime <= 0) {
            throw new IllegalArgumentException();
        }
        this.averageTime = averageTime;
    }
}
