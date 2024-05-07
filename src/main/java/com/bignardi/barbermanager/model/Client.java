package com.bignardi.barbermanager.model;

import java.util.Date;

public class Client {
    private String name;
    private int averageTime = 20;
    private int date;

    public Client(String name, int averageTime) {
        this.name = name;
        this.averageTime = averageTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
