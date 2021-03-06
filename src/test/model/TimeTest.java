package model;

import exception.IllegalHourException;
import exception.IllegalMinuteException;
import exception.IllegalTimeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TimeTest extends ModelTest {

    private Time testTime, minutesLater, minutesBefore, hoursLater, hoursBefore;

    @Test
    public void testConstructorNoException() {
        try {
            testTime = new Time(6, 30);
        } catch (IllegalTimeException e) {
            fail(FAIL_MSG_UEET);
        }

        assertEquals(testTime.getHour(), 6);
        assertEquals(testTime.getMinute(), 30);
    }

    @Test
    public void testConstructorIllegalHourException() {
        try {
            testTime = new Time(25, 30);
            fail(FAIL_MSG_EENT);
        } catch (IllegalHourException e) {
            // expected
        } catch (IllegalMinuteException e) {
            fail(FAIL_MSG_UEET);
        }

        try {
            testTime = new Time(-1, 30);
            fail(FAIL_MSG_EENT);
        } catch (IllegalHourException e) {
            // expected
        } catch (IllegalMinuteException e) {
            fail(FAIL_MSG_UEET);
        }
    }

    @Test
    public void testConstructorIllegalMinuteException() {
        try {
            testTime = new Time(12, 61);
            fail(FAIL_MSG_EENT);
        } catch (IllegalHourException e) {
            fail(FAIL_MSG_UEET);
        } catch (IllegalMinuteException e) {
            // expected
        }

        try {
            testTime = new Time(12, -1);
            fail(FAIL_MSG_EENT);
        } catch (IllegalHourException e) {
            fail(FAIL_MSG_UEET);
        } catch (IllegalMinuteException e) {
            // expected
        }
    }

    @Test
    public void testParseHourNoException() {
        try {
            assertEquals(Time.parseHour("11:30"), 11);
        } catch (IllegalTimeException e) {
            fail(FAIL_MSG_UEET);
        }
    }

    @Test
    public void testParseHourIllegalTimeException() {
        try {
            Time.parseHour("1130");
            fail(FAIL_MSG_EENT);
        } catch (IllegalTimeException e) {
            // expected
        }

        try {
            Time.parseHour("11a:30");
            fail(FAIL_MSG_EENT);
        } catch (IllegalTimeException e) {
            // expected
        }

        try {
            Time.parseHour("28:30");
            fail(FAIL_MSG_EENT);
        } catch (IllegalTimeException e) {
            // expected
        }

        try {
            Time.parseHour("-1:30");
            fail(FAIL_MSG_EENT);
        } catch (IllegalTimeException e) {
            // expected
        }
    }

    @Test
    public void testParseMinuteNoException() {
        try {
            assertEquals(Time.parseMinute("11:30"), 30);
        } catch (IllegalTimeException e) {
            fail(FAIL_MSG_UEET);
        }
    }

    @Test
    public void testParseMinuteIllegalTimeException() {
        try {
            Time.parseMinute("1130");
            fail(FAIL_MSG_EENT);
        } catch (IllegalTimeException e) {
            // expected
        }

        try {
            Time.parseMinute("11:30am");
            fail(FAIL_MSG_EENT);
        } catch (IllegalTimeException e) {
            // expected
        }

        try {
            Time.parseMinute("11:65");
            fail(FAIL_MSG_EENT);
        } catch (IllegalTimeException e) {
            // expected
        }

        try {
            Time.parseMinute("11:-1");
            fail(FAIL_MSG_EENT);
        } catch (IllegalTimeException e) {
            // expected
        }
    }

    @Test
    public void testLaterThan() {
        try {
            testTime = new Time(7, 30);
            minutesLater = new Time(7, 50);
            minutesBefore = new Time(7, 20);
            hoursLater = new Time(9, 30);
            hoursBefore = new Time(5, 30);
        } catch (IllegalTimeException e) {
            fail(FAIL_MSG_UEET);
        }

        assertTrue(testTime.laterThan(testTime));
        assertTrue(testTime.laterThan(minutesBefore));
        assertTrue(testTime.laterThan(hoursBefore));
        assertFalse(testTime.laterThan(minutesLater));
        assertFalse(testTime.laterThan(hoursLater));
    }

    @Test
    public void testEquals() {
        try {
            testTime = new Time(6, 30);
            assertEquals(testTime, new Time(6, 30));
            assertNotEquals(testTime, new Time(7, 30));
            assertNotEquals(testTime, new Time(6, 40));
            assertEquals(testTime, testTime);
            assertNotEquals(testTime, null);
            assertNotEquals(testTime, new Object());
        } catch (IllegalTimeException e) {
            fail(FAIL_MSG_UEET);
        }
    }

    @Test
    public void testHashCode() {
        try {
            testTime = new Time(6, 30);
            assertEquals(testTime.hashCode(), new Time(6, 30).hashCode());
            assertNotEquals(testTime.hashCode(), new Time(7, 30).hashCode());
            assertNotEquals(testTime.hashCode(), new Time(7, 40).hashCode());
        } catch (IllegalTimeException e) {
            fail(FAIL_MSG_UEET);
        }
    }

    @Test
    public void testToString() {
        try {
            testTime = new Time(7, 30);
        } catch (IllegalTimeException e) {
            fail(FAIL_MSG_UEET);
        }
        assertEquals(testTime.toString(), "07:30");

        try {
            testTime = new Time(19, 30);
        } catch (IllegalTimeException e) {
            fail(FAIL_MSG_UEET);
        }
        assertEquals(testTime.toString(), "19:30");
    }
}