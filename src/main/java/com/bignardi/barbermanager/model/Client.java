package com.bignardi.barbermanager.model;

public class Client {
    private String name;
    private int duration;

    public Client() {
    }

    public Client(String name, int duration) {
        if (name != null && duration > 0) {
            this.name = name;
            this.duration = duration;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        if (duration > 0) {
            this.duration = duration;
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public String toStringFull() {
        return name + " " + duration;
    }
}
