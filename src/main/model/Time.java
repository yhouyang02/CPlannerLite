package model;

import exception.IllegalHourException;
import exception.IllegalMinuteException;

// Represents a 24-hour time with hours and minutes.
public class Time {

    private int hour;
    private int minute;

    // EFFECTS: if hour < 0 or hour > 24, throws IllegalHourException;
    //          if minute < 0 or minute > 60, throws IllegalMinuteException;
    //          otherwise, constructs a new time with hours and minutes
    public Time(int hour, int minute) throws IllegalHourException, IllegalMinuteException {
        if (hour < 0 || hour > 24) {
            throw new IllegalHourException();
        } else if (minute < 0 || minute > 60) {
            throw new IllegalMinuteException();
        }
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    // EFFECTS: else, returns true if this time comes later than t, false otherwise
    public boolean laterThan(Time t) {
        if (hour == t.getHour()) {
            return minute >= t.getMinute();
        }
        return hour >= t.getHour();
    }

    // EFFECTS: returns a string of time in format "HH:MM"
    @Override
    public String toString() {
        return String.format("%02d", hour) + ":" + String.format("%02d", minute);
    }

}
