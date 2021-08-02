package model;

import exception.IllegalDaysException;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Arrays;
import java.util.Objects;

// Represents the schedule of a university course with a
// start time, an end time, and days of meeting (of a week)
public class Schedule implements Writable {

    // Common meeting days for university courses
    public static final boolean[] MEETING_DAYS_MWF = {true, false, true, false, true};
    public static final boolean[] MEETING_DAYS_TT = {false, true, false, true, false};
    public static final boolean[] MEETING_DAYS_MTTF = {true, true, false, true, true};

    private static final String[] STR_WEEKDAY_ABBR = {"Mon", "Tue", "Wed", "Thu", "Fri"};

    private boolean[] days;
    private Time startTime;
    private Time endTime;

    // EFFECTS: if days has a length other than 5, throws IllegalDaysException;
    //          otherwise, constructs a new schedule with days of meeting, start time, and end time
    public Schedule(boolean[] days, Time start, Time end) throws IllegalDaysException {
        if (days.length != 5) {
            throw new IllegalDaysException();
        }
        this.days = days;
        this.startTime = start;
        this.endTime = end;
    }

    // EFFECTS: parses string s as a list of boolean with length == 5;
    //          throws IllegalDaysException when s does not have a length of 5, and
    //                                      when s is not completely composed of 'T' and 'F', case-insensitive
    public static boolean[] parseDays(String s) throws IllegalDaysException {
        boolean[] tempDays = new boolean[5];
        if (s.length() != 5) {
            throw new IllegalDaysException();
        } else {
            for (int i = 0; i < 5; i++) {
                String tempStr = s.substring(i, i + 1);
                if (!tempStr.equalsIgnoreCase("T") && !tempStr.equalsIgnoreCase("F")) {
                    throw new IllegalDaysException();
                } else {
                    tempDays[i] = tempStr.equalsIgnoreCase("T");
                }
            }
        }
        return tempDays;
    }

    // EFFECTS: returns the meeting days of this schedule
    public boolean[] getDays() {
        return days;
    }

    // EFFECTS: returns the starting time of this schedule
    public Time getStartTime() {
        return startTime;
    }

    // EFFECTS: returns the ending time of this schedule
    public Time getEndTime() {
        return endTime;
    }

    // EFFECTS: returns true if this schedule overlaps s, false otherwise
    public boolean isOverlapping(Schedule s) {
        for (int i = 0; i < days.length; i++) {
            if (days[i] && s.getDays()[i]) {
                if (!startTime.laterThan(s.getEndTime()) && !s.getStartTime().laterThan(endTime)) {
                    return true;
                }
            }
        }
        return false;
    }

    // EFFECTS: returns true if this schedule has meeting on the given day, false otherwise
    public boolean hasMeeting(Weekday day) {
        switch (day) {
            case MONDAY:
                return days[0];
            case TUESDAY:
                return days[1];
            case WEDNESDAY:
                return days[2];
            case THURSDAY:
                return days[3];
            default:
                return days[4];
        }
    }

    // EFFECTS: returns a string of schedule in format like "<days>\tHH:MM - HH:MM";
    //          e.g., "Mon Wed Fri  12:00 - 13:00"
    @Override
    public String toString() {
        return getMeetingDaysString() + "\t" + getTimeString();
    }

    // EFFECTS: returns a string of days when there are meetings
    private String getMeetingDaysString() {
        String str = "";
        for (int i = 0; i < days.length; i++) {
            if (days[i]) {
                str = str.concat(STR_WEEKDAY_ABBR[i] + " ");
            }
        }
        return str;
    }

    // EFFECTS: returns a string of time period from startTime to EndTime in format "HH:MM - HH:MM"
    private String getTimeString() {
        return startTime.toString() + " - " + endTime.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Schedule schedule = (Schedule) o;
        return Arrays.equals(days, schedule.days)
                && startTime.equals(schedule.startTime)
                && endTime.equals(schedule.endTime);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(startTime, endTime);
        result = 31 * result + Arrays.hashCode(days);
        return result;
    }

    // EFFECTS: returns this schedule as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("days", days);
        json.put("startHour", startTime.getHour());
        json.put("startMinute", startTime.getMinute());
        json.put("endHour", endTime.getHour());
        json.put("endMinute", endTime.getMinute());
        return json;
    }

}
