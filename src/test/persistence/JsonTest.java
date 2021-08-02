package persistence;

import exception.IllegalDaysException;
import exception.IllegalTimeException;
import model.Course;
import model.Schedule;
import model.Time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// NOTE: This class is adjusted from JsonTest.java; Link below:
//       https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/test/persistence/JsonTest.java
public class JsonTest {

    protected static final String FAIL_MSG_EENT = "Expected exception not thrown";
    protected static final String FAIL_MSG_UEET = "Unexpected exception thrown";

    protected Schedule testSchedule1, testSchedule2, testSchedule3;
    protected Course testCourse1, testCourse2, testCourse3;

    protected void initSchedules() {
        try {
            testSchedule1 = new Schedule(Schedule.MEETING_DAYS_MWF,
                    new Time(12, 0), new Time(13, 0));
            testSchedule2 = new Schedule(Schedule.MEETING_DAYS_TT,
                    new Time(17, 0), new Time(18, 30));
            testSchedule3 = new Schedule(Schedule.MEETING_DAYS_TT,
                    new Time(9, 30), new Time(11, 0));
        } catch (IllegalDaysException | IllegalTimeException e) {
            fail(FAIL_MSG_UEET);
        }
    }

    protected void initCourses() {
        initSchedules();
        testCourse1 = new Course("CPSC", "221", "103",
                "Basic Algorithms and Data Structures",
                testSchedule1, 4, true, true);
        testCourse2 = new Course("CPSC", "213", "102",
                "Introduction to Computer Systems",
                testSchedule2, 4, true, false);
        testCourse3 = new Course("PSYC", "305A", "001",
                "Personality Psychology",
                testSchedule3, 3, false, false);
    }

    protected void checkCourse(String subjectCode, String courseCode, String sectionCode,
                               String title, Schedule schedule, int credits, boolean required,
                               boolean starred, Course course) {
        assertEquals(course.getSubjectCode(), subjectCode);
        assertEquals(course.getCourseCode(), courseCode);
        assertEquals(course.getSectionCode(), sectionCode);
        assertEquals(course.getTitle(), title);
        assertEquals(course.getSchedule(), schedule);
        assertEquals(course.getCredits(), credits);
        assertEquals(course.isRequired(), required);
        assertEquals(course.isStarred(), starred);
    }

}
