package model;

// Represents the schedule of a university course with
// start time, end time, and days of meeting (of a week).
public class Schedule {

    private boolean[] days;
    private Time startTime;
    private Time endTime;

    // REQUIRES: days.length() == 5
    // EFFECTS: constructs a new schedule with days of meeting, start time, and end time
    public Schedule(boolean[] days, Time start, Time end) {
        // stub
    }

    public boolean[] getDays() {
        return new boolean[5]; // stub
    }

    public Time getStartTime() {
        return null; // stub
    }

    public Time getEndTime() {
        return null; // stub
    }

    // EFFECTS: returns true if this schedule overlaps s, false otherwise
    public boolean isOverlapping(Schedule s) {
        return false; // stub
    }

    // EFFECTS: returns true if this schedule has meeting on the given day, false otherwise
    public boolean hasMeeting(Weekday day) {
        return false; // stub
    }

    // EFFECTS: returns a string of schedule in format like "<days>\tHH:MM - HH:MM";
    //          e.g., "Mon Wed Fri  12:00 - 13:00"
    @Override
    public String toString() {
        return null; // stub
    }

}
