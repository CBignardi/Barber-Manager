package com.bignardi.barbermanager.model;

public class DayDate {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;


    public DayDate(int year, int month, int day, int hour, int minute) throws IllegalArgumentException {
        if (day > 31 || day < 0 || month > 13 || month < 0 || year < 0 || hour > 24 || hour < 0 || minute >= 60 || minute < 0) {
            throw new IllegalArgumentException();
        }
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public String toString() {
        return "DayDate{" +
                "day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", hour=" + hour +
                ", minute=" + minute +
                '}';
    }

    public String toStringFormat() {
        StringBuilder dst = new StringBuilder();
        if (hour < 10) {
            dst.append("0");
        }
        dst.append(hour);
        dst.append(":");

        if (minute < 10) {
            dst.append("0");
        }
        dst.append(minute);

        return dst.toString();
    }


}
