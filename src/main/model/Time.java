package model;

import exception.IllegalHourException;
import exception.IllegalMinuteException;
import exception.IllegalTimeException;

// Represents a 24-hour time with hour and minute
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

    // EFFECTS: parses string s as a time and returns the hour of the time;
    //          throws IllegalTimeException when s is not in format of "HH:MM", where HH and MM are integers, and
    //                                      when HH is not between 0 and 24
    public static int parseHour(String s) throws IllegalTimeException {
        int i = s.indexOf(":");
        int hour;
        if (i == -1) {
            throw new IllegalTimeException();
        } else {
            try {
                hour = Integer.parseInt(s.substring(0, i));
            } catch (NumberFormatException e) {
                throw new IllegalTimeException();
            }
            if (hour < 0 || hour > 24) {
                throw new IllegalHourException();
            }
        }
        return hour;
    }

    // EFFECTS: parses string s as a time and returns the minute of the time;
    //          throws IllegalTimeException when s is not in format of "HH:MM", where HH and MM are integers, and
    //                                      when MM is not between 0 and 60
    public static int parseMinute(String s) throws IllegalTimeException {
        int i = s.indexOf(":");
        int minute;
        if (i == -1) {
            throw new IllegalTimeException();
        } else {
            try {
                minute = Integer.parseInt(s.substring(i + 1));
            } catch (NumberFormatException e) {
                throw new IllegalTimeException();
            }
            if (minute < 0 || minute > 60) {
                throw new IllegalMinuteException();
            }
        }
        return minute;
    }

    // EFFECTS: returns the hour of this time
    public int getHour() {
        return hour;
    }

    // EFFECTS: returns the minute of this time
    public int getMinute() {
        return minute;
    }

    // EFFECTS: returns true if this time comes later than t, false otherwise
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
