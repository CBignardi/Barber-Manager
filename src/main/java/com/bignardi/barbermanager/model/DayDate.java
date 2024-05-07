package com.bignardi.barbermanager.model;

public class DayDate {
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;

    public DayDate(int day, int month, int year, int hour, int minute) throws IllegalArgumentException {
        if(day > 31 || day < 0 || month > 13 || month < 0 || year < 0 || hour > 24 || hour < 0 || minute >= 60 || minute < 0){
            throw new IllegalArgumentException();
        }
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }
}
