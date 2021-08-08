package model;

import exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorklistTest extends ModelTest {

    private Course testCourse1, testCourse2, testCourse3, testCourse4;
    private Worklist testWorklist;

    @BeforeEach
    public void init() {
        testWorklist = new Worklist("2021 WT1");
        initCourses();
    }

    private void initCourses() {
        try {
            testCourse1 = new Course("CPSC", "221", "103",
                    "Basic Algorithms and Data Structures",
                    new Schedule(Schedule.MEETING_DAYS_MWF,
                            new Time(12, 0),
                            new Time(13, 0)),
                    4, true);
            testCourse2 = new Course("CPSC", "213", "102",
                    "Introduction to Computer Systems",
                    new Schedule(Schedule.MEETING_DAYS_TT,
                            new Time(17, 0),
                            new Time(18, 30)),
                    4, true);
            testCourse3 = new Course("PSYC", "305A", "001",
                    "Personality Psychology",
                    new Schedule(Schedule.MEETING_DAYS_TT,
                            new Time(9, 30),
                            new Time(11, 0)),
                    3, false);
            testCourse4 = new Course("MATH", "256", "101",
                    "Differential Equations",
                    new Schedule(Schedule.MEETING_DAYS_MWF,
                            new Time(12, 0),
                            new Time(13, 0)),
                    3, false);
        } catch (IllegalDaysException | IllegalTimeException e) {
            fail(FAIL_MSG_UEET);
        }
    }

    private void addValidCourses() {
        try {
            testWorklist.addCourse(testCourse1);
            testWorklist.addCourse(testCourse2);
            testWorklist.addCourse(testCourse3);
        } catch (CourseAlreadyExistsException | CourseConflictsException e) {
            fail(FAIL_MSG_UEET);
        }
    }

    @Test
    public void testConstructor() {
        assertEquals(testWorklist.getName(), "2021 WT1");
        assertEquals(testWorklist.getCourses().size(), 0);
    }

    @Test
    public void testAddCourseNoException() {
        addValidCourses();
        assertEquals(testWorklist.getCourses().size(), 3);
    }

    @Test
    public void testAddCourseCourseAlreadyExistsException() {
        addValidCourses();
        assertEquals(testWorklist.getCourses().size(), 3);

        try {
            testWorklist.addCourse(testCourse2);
            fail(FAIL_MSG_EENT);
        } catch (CourseAlreadyExistsException e) {
            // expected
        } catch (CourseConflictsException e) {
            fail(FAIL_MSG_UEET);
        }
        assertEquals(testWorklist.getCourses().size(), 3);

        Course sameCourse;
        try {
            sameCourse = new Course("CPSC", "221", "103");
            testWorklist.addCourse(sameCourse);
            fail(FAIL_MSG_EENT);
        } catch (CourseAlreadyExistsException e) {
            // expected
        } catch (CourseConflictsException e) {
            fail(FAIL_MSG_UEET);
        }
        assertEquals(testWorklist.getCourses().size(), 3);
    }

    @Test
    public void testAddCourseCourseConflictsException() {
        addValidCourses();
        assertEquals(testWorklist.getCourses().size(), 3);

        try {
            testWorklist.addCourse(testCourse4);
            fail(FAIL_MSG_EENT);
        } catch (CourseAlreadyExistsException e) {
            fail(FAIL_MSG_UEET);
        } catch (CourseConflictsException e) {
            // expected
        }
        assertEquals(testWorklist.getCourses().size(), 3);
    }

    @Test
    public void testDeleteCourseNoException() {
        addValidCourses();
        assertEquals(testWorklist.getCourses().size(), 3);

        try {
            testWorklist.deleteCourse(testCourse1);
            assertEquals(testWorklist.getCourses().size(), 2);
            testWorklist.deleteCourse(testCourse2);
            assertEquals(testWorklist.getCourses().size(), 1);
            testWorklist.deleteCourse(testCourse3);
            assertEquals(testWorklist.getCourses().size(), 0);
        } catch (CourseNotFoundException e) {
            fail(FAIL_MSG_UEET);
        }
    }

    @Test
    public void testDeleteCourseCourseNotFoundException() {
        try {
            testWorklist.deleteCourse(testCourse1);
            fail(FAIL_MSG_EENT);
        } catch (CourseNotFoundException e) {
            // expected
        }

        addValidCourses();
        assertEquals(testWorklist.getCourses().size(), 3);

        try {
            testWorklist.deleteCourse(testCourse4);
            fail(FAIL_MSG_EENT);
        } catch (CourseNotFoundException e) {
            // expected
        }
        assertEquals(testWorklist.getCourses().size(), 3);
    }

    @Test
    public void testSetRequiredNoException() {
        addValidCourses();
        assertTrue(testCourse1.isRequired());
        try {
            testWorklist.setRequired(new Course("CPSC", "221", "103"), false);
        } catch (CourseNotFoundException e) {
            fail(FAIL_MSG_UEET);
        }
        assertFalse(testCourse1.isRequired());
        try {
            testWorklist.setRequired(new Course("CPSC", "221", "103"), true);
        } catch (CourseNotFoundException e) {
            fail(FAIL_MSG_UEET);
        }
        assertTrue(testCourse1.isRequired());
    }

    @Test
    public void testSetRequiredCourseNotFoundException() {
        addValidCourses();
        try {
            testWorklist.setRequired(new Course("MATH", "200", "101"), true);
            fail(FAIL_MSG_EENT);
        } catch (CourseNotFoundException e) {
            // expected
        }
    }

    @Test
    public void testStarUnstarNoException() {
        addValidCourses();
        assertFalse(testCourse1.isStarred());
        try {
            testWorklist.starCourse(new Course("CPSC", "221", "103"));
        } catch (CourseNotFoundException e) {
            fail(FAIL_MSG_UEET);
        }
        assertTrue(testCourse1.isStarred());

        try {
            testWorklist.unstarCourse(new Course("CPSC", "221", "103"));
        } catch (CourseNotFoundException e) {
            fail(FAIL_MSG_UEET);
        }
        assertFalse(testCourse1.isStarred());
    }

    @Test
    public void testStarUnstarCourseNotFoundException() {
        addValidCourses();
        try {
            testWorklist.starCourse(new Course("MATH", "200", "101"));
            fail(FAIL_MSG_EENT);
        } catch (CourseNotFoundException e) {
            // expected
        }

        try {
            testWorklist.unstarCourse(new Course("MATH", "200", "101"));
            fail(FAIL_MSG_EENT);
        } catch (CourseNotFoundException e) {
            // expected
        }
    }

    @Test
    public void testGetStarredUnstarredCourses() {
        addValidCourses();
        try {
            testWorklist.starCourse(new Course("CPSC", "221", "103"));
            testWorklist.starCourse(new Course("CPSC", "213", "102"));
        } catch (Exception e) {
            fail(FAIL_MSG_UEET);
        }
        assertEquals(testWorklist.getStarredCourses().size(), 2);
        assertEquals(testWorklist.getUnstarredCourses().size(), 1);
    }

    @Test
    public void testGetTotalCredits() {
        addValidCourses();
        assertEquals(testWorklist.getTotalCredits(), 4 + 4 + 3);
    }

    @Test
    public void testGetSubjectCodes() {
        addValidCourses();
        assertEquals(testWorklist.getSubjectCodes().size(), 2);
        assertTrue(testWorklist.getSubjectCodes().contains("CPSC"));
        assertTrue(testWorklist.getSubjectCodes().contains("PSYC"));
    }

    @Test
    public void testGetStarredUnstarredCodes() {
        addValidCourses();
        try {
            testWorklist.starCourse(new Course("CPSC", "221", "103"));
            testWorklist.starCourse(new Course("CPSC", "213", "102"));
        } catch (Exception e) {
            fail(FAIL_MSG_UEET);
        }

        assertEquals(testWorklist.getStarredCodes().size(), 2);
        assertTrue(testWorklist.getStarredCodes().contains("CPSC 221 103"));
        assertTrue(testWorklist.getStarredCodes().contains("CPSC 213 102"));

        assertEquals(testWorklist.getUnstarredCodes().size(), 1);
        assertTrue(testWorklist.getUnstarredCodes().contains("PSYC 305A 001"));
    }

    @Test
    public void testGetNumSubject() {
        addValidCourses();
        assertEquals(testWorklist.getNumCoursesOfSubject("CPSC"), 2);
        assertEquals(testWorklist.getNumCoursesOfSubject("PSYC"), 1);
        assertEquals(testWorklist.getNumCoursesOfSubject("MATH"), 0);
    }

    @Test
    public void testGetNumRequiredOptionalCourses() {
        addValidCourses();
        assertEquals(testWorklist.getNumRequiredCourses(), 2);
        assertEquals(testWorklist.getNumOptionalCourses(), 1);
    }

}