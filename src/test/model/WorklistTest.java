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
        // course already added in init()
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
            sameCourse = new Course("CPSC", "221", "102",
                    "Basic Algorithms and Data Structures",
                    new Schedule(Schedule.MEETING_DAYS_MWF,
                            new Time(12, 0),
                            new Time(13, 0)),
                    4, true);
            testWorklist.addCourse(sameCourse);
            fail(FAIL_MSG_EENT);
        } catch (CourseAlreadyExistsException e) {
            // expected
        } catch (CourseConflictsException | IllegalDaysException | IllegalTimeException e) {
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
    public void testDeleteCourseException() {
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
            testWorklist.setRequired("CPSC 221", false);
        } catch (CourseNotFoundException e) {
            fail(FAIL_MSG_UEET);
        }
        assertFalse(testCourse1.isRequired());
        try {
            testWorklist.setRequired("CPSC 221", true);
        } catch (CourseNotFoundException e) {
            fail(FAIL_MSG_UEET);
        }
        assertTrue(testCourse1.isRequired());
    }

    @Test
    public void testSetRequiredException() {
        addValidCourses();
        try {
            testWorklist.setRequired("MATH 200", true);
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
            testWorklist.star("CPSC 221");
        } catch (CourseNotFoundException e) {
            fail(FAIL_MSG_UEET);
        }
        assertTrue(testCourse1.isStarred());
        try {
            testWorklist.unstar("CPSC 221");
        } catch (CourseNotFoundException e) {
            fail(FAIL_MSG_UEET);
        }
        assertFalse(testCourse1.isStarred());
    }

    @Test
    public void testStarUnstarException() {
        addValidCourses();
        try {
            testWorklist.star("MATH 200");
            fail(FAIL_MSG_EENT);
        } catch (CourseNotFoundException e) {
            // expected
        }

        try {
            testWorklist.unstar("MATH 200");
            fail(FAIL_MSG_EENT);
        } catch (CourseNotFoundException e) {
            // expected
        }
    }

    @Test
    public void testGetStarredCourses() {
        addValidCourses();
        try {
            testWorklist.star("CPSC 221");
            testWorklist.star("CPSC 213");
        } catch (Exception e) {
            fail(FAIL_MSG_UEET);
        }
        assertEquals(testWorklist.getStarredCourses().size(), 2);
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