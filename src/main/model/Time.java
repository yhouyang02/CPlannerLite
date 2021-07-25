package model;

// Represents a 24-hour time with hours and minutes.
public class Time {

    private int hour;
    private int minute;

    // REQUIRES: hours and minutes should both be valid in 24-hour format;
    //           i.e. 0 <= hours <= 24
    //                0 <= minutes <= 60
    // EFFECTS: constructs a new time with hours and minutes
    public Time(int hour, int minute) {
        // stub
    }

    public int getHours() {
        return 0; // stub
    }

    public int getMinutes() {
        return 0; // stub
    }

    // EFFECTS: returns true if this time comes later than t, false otherwise
    public boolean laterThan(Time t) {
        return false;
    }

    // EFFECTS: returns a string of time in format "HH:MM"
    @Override
    public String toString() {
        return null; // stub
    }

}
