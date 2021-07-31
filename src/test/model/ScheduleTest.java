package model;

import exception.IllegalDaysException;
import exception.IllegalTimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Schedule.MEETING_DAYS_MTTF;
import static model.Schedule.MEETING_DAYS_MWF;
import static org.junit.jupiter.api.Assertions.*;

public class ScheduleTest extends ModelTest {

    private Schedule testSchedule, notOverlapped, sameDaysOverlapped, differentDaysOverlapped;
    private Time testStartTime, testEndTime;
    private boolean[] ILLEGAL_DAYS = {false, false, false, false, false, false};

    @BeforeEach
    public void init() {
        try {
            testStartTime = new Time(10, 0);
            testEndTime = new Time(11, 0);
            testSchedule = new Schedule(MEETING_DAYS_MWF, testStartTime, testEndTime);
        } catch (IllegalDaysException | IllegalTimeException e) {
            fail(FAIL_MSG_UEET);
        }
    }

    @Test
    public void testParseDaysNoException() {
        try {
            assertEquals(Schedule.parseDays("TFTFT").length, 5);
            assertEquals(Schedule.parseDays("tftft").length, 5);
        } catch (IllegalDaysException e) {
            fail(FAIL_MSG_UEET);
        }
    }

    @Test
    public void testParseDaysIllegalDaysException() {
        try {
            assertEquals(Schedule.parseDays("TFTFTF").length, 5);
        } catch (IllegalDaysException e) {
            // expected
        }

        try {
            assertEquals(Schedule.parseDays("FTGTF").length, 5);
        } catch (IllegalDaysException e) {
            // expected
        }

        try {
            assertEquals(Schedule.parseDays("ftgtf").length, 5);
        } catch (IllegalDaysException e) {
            // expected
        }


    }

    @Test
    public void testConstructorNoException() {
        assertEquals(testSchedule.getDays(), MEETING_DAYS_MWF);
        assertEquals(testSchedule.getStartTime(), testStartTime);
        assertEquals(testSchedule.getEndTime(), testEndTime);
    }

    @Test
    public void testConstructorException() {
        try {
            testSchedule = new Schedule(ILLEGAL_DAYS, testStartTime, testEndTime);
            fail(FAIL_MSG_EENT);
        } catch (IllegalDaysException e) {
            // expected
        }
    }

    @Test
    public void testIsOverlapping() {
        try {
            notOverlapped = new Schedule(MEETING_DAYS_MWF,
                    new Time(11, 0), new Time(12, 0));
            sameDaysOverlapped = new Schedule(MEETING_DAYS_MWF,
                    new Time(10, 30), new Time(12, 0));
            differentDaysOverlapped = new Schedule(MEETING_DAYS_MTTF,
                    new Time(10, 30), new Time(11, 30));
        } catch (IllegalDaysException | IllegalTimeException e) {
            fail(FAIL_MSG_UEET);
        }

        assertFalse(testSchedule.isOverlapping(notOverlapped));
        assertTrue(testSchedule.isOverlapping(sameDaysOverlapped));
        assertTrue(testSchedule.isOverlapping(differentDaysOverlapped));
    }

    @Test
    public void testHasMeeting() {
        assertTrue(testSchedule.hasMeeting(Weekday.MONDAY));
        assertFalse(testSchedule.hasMeeting(Weekday.TUESDAY));
        assertTrue(testSchedule.hasMeeting(Weekday.WEDNESDAY));
        assertFalse(testSchedule.hasMeeting(Weekday.THURSDAY));
        assertTrue(testSchedule.hasMeeting(Weekday.FRIDAY));
    }

    @Test
    public void testToString() {
        assertEquals(testSchedule.toString(), "Mon Wed Fri \t10:00 - 11:00");
    }

}
