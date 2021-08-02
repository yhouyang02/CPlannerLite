package model;

import exception.IllegalCodesException;
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
    public void testParseCodesNoException() {
        try {
            assertEquals(Course.parseSubjectCode("CPSC 210 921"), "CPSC");
            assertEquals(Course.parseCourseCode("CPSC 210 921"), "210");
            assertEquals(Course.parseSectionCode("CPSC 210 921"), "921");
        } catch (IllegalCodesException e) {
            fail(FAIL_MSG_UEET);
        }
    }

    @Test
    public void testParseCodesIllegalCodesException() {
        try {
            Course.parseSubjectCode("CPSC210921");
            fail(FAIL_MSG_EENT);
        } catch (IllegalCodesException e) {
            // expected
        }

        try {
            Course.parseSubjectCode("CPSC210 921");
            fail(FAIL_MSG_EENT);
        } catch (IllegalCodesException e) {
            // expected
        }

        try {
            Course.parseCourseCode("CPSC210921");
            fail(FAIL_MSG_EENT);
        } catch (IllegalCodesException e) {
            // expected
        }

        try {
            Course.parseCourseCode("CPSC210 921");
            fail(FAIL_MSG_EENT);
        } catch (IllegalCodesException e) {
            // expected
        }

        try {
            Course.parseSectionCode("CPSC210921");
            fail(FAIL_MSG_EENT);
        } catch (IllegalCodesException e) {
            // expected
        }

        try {
            Course.parseSectionCode("CPSC210 921");
            fail(FAIL_MSG_EENT);
        } catch (IllegalCodesException e) {
            // expected
        }
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

    @Test
    public void testEquals() {
        assertEquals(testCourse, new Course("CPSC", "210", "102"));
        assertNotEquals(testCourse, new Course("CPSC", "210", "103"));
        assertNotEquals(testCourse, new Course("CPSC", "213", "102"));
        assertNotEquals(testCourse, new Course("MATH", "210", "102"));
        assertEquals(testCourse, testCourse);
        assertNotEquals(testCourse, null);
        assertNotEquals(testCourse, new Object());
    }

    @Test
    public void testHashCode() {
        assertEquals(testCourse.hashCode(), new Course("CPSC", "210", "102").hashCode());
        assertNotEquals(testCourse.hashCode(), new Course("CPSC", "210", "103").hashCode());
        assertNotEquals(testCourse.hashCode(), new Course("CPSC", "213", "102").hashCode());
        assertNotEquals(testCourse.hashCode(), new Course("MATH", "210", "102").hashCode());
    }

}
