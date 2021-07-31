package model;

import exception.IllegalDaysException;
import exception.IllegalTimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest extends ModelTest {

    private Time testStartTime, testEndTime;
    private Schedule testSchedule;
    private Course testCourse;

    @BeforeEach
    public void init() {
        try {
            testStartTime = new Time(13, 0);
            testEndTime = new Time(14, 0);
            testSchedule = new Schedule(Schedule.MEETING_DAYS_MWF, testStartTime, testEndTime);
        } catch (IllegalTimeException | IllegalDaysException e) {
            fail(FAIL_MSG_UEET);
        }

        testCourse = new Course("CPSC", "210", "102",
                "Software Construction", testSchedule, 4, true);
    }

    @Test
    public void testConstructor() {
        assertEquals(testCourse.getSubjectCode(), "CPSC");
        assertEquals(testCourse.getCourseCode(), "210");
        assertEquals(testCourse.getSectionCode(), "102");
        assertEquals(testCourse.getTitle(), "Software Construction");
        assertEquals(testCourse.getComments(), "");
        assertEquals(testCourse.getCredits(), 4);
        assertEquals(testCourse.getSchedule(), testSchedule);
        assertTrue(testCourse.isRequired());
        assertFalse(testCourse.isStarred());
    }

    @Test
    public void testParseRequiredNoException() {
        try {
            assertTrue(Course.parseRequired("T"));
            assertTrue(Course.parseRequired("t"));
            assertFalse(Course.parseRequired("F"));
            assertFalse(Course.parseRequired("f"));
        } catch (IllegalArgumentException e) {
            fail(FAIL_MSG_UEET);
        }
    }

    @Test
    public void testParseRequiredIllegalArgumentException() {
        try {
            Course.parseRequired("A");
            fail(FAIL_MSG_EENT);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testSetComments() {
        assertEquals(testCourse.getComments(), "");
        testCourse.setComments("This course will be online.");
        assertEquals(testCourse.getComments(), "This course will be online.");
    }

    @Test
    public void testSetRequired() {
        assertTrue(testCourse.isRequired());
        testCourse.setRequired(false);
        assertFalse(testCourse.isRequired());
    }

    @Test
    public void testStarUnstar() {
        assertFalse(testCourse.isStarred());

        assertTrue(testCourse.star());
        assertTrue(testCourse.isStarred());
        assertFalse(testCourse.star());
        assertTrue(testCourse.isStarred());

        assertTrue(testCourse.unstar());
        assertFalse(testCourse.isStarred());
        assertFalse(testCourse.unstar());
        assertFalse(testCourse.isStarred());
    }

    @Test
    public void testGetSubjectCourseCode() {
        assertEquals(testCourse.getSubjectCourseCode(), "CPSC 210");
    }

    @Test
    public void testToString() {
        assertEquals(testCourse.toString(),
                "CPSC 210 102 - Software Construction\tMon Wed Fri \t13:00 - 14:00");
    }

}
