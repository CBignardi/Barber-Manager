package com.bignardi.barbermanager.model;

public class Client {
    private String Name;
    private int AverageTime = 20;

    public Client(String name, int averageTime) {
        Name = name;
        AverageTime = averageTime;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAverageTime() {
        return AverageTime;
    }

    public void setAverageTime(int averageTime) throws IllegalArgumentException {
        if (averageTime <= 0) {
            throw new IllegalArgumentException();
        }
        AverageTime = averageTime;
    }
}
