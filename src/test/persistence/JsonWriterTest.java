package persistence;

import exception.CourseAlreadyExistsException;
import exception.CourseConflictsException;
import model.Course;
import model.Worklist;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// NOTE: This class is adjusted from JsonWriterTest.java; Link below:
//       https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/test/persistence/JsonWriterTest.java
public class JsonWriterTest extends JsonTest {

    @Test
    public void testWriterInvalidFile() {
        try {
            Worklist testWorklist = new Worklist("Test Worklist");

            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            writer.write(testWorklist);
            writer.close();
            fail(FAIL_MSG_EENT);
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void testWriterEmptyWorklist() {
        try {
            Worklist testWorklist = new Worklist("Test Worklist");

            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorklist.json");
            writer.open();
            writer.write(testWorklist);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorklist.json");
            testWorklist = reader.read();
            assertEquals("Test Worklist", testWorklist.getName());
            assertEquals(0, testWorklist.getCourses().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterGeneralWorklist() {
        try {
            initCourses();
            Worklist testWorklist = new Worklist("Test Worklist");
            testWorklist.addCourse(testCourse1);
            testWorklist.addCourse(testCourse2);
            testWorklist.addCourse(testCourse3);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorklist.json");
            writer.open();
            writer.write(testWorklist);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorklist.json");
            testWorklist = reader.read();
            assertEquals("Test Worklist", testWorklist.getName());
            List<Course> testCourses = testWorklist.getCourses();
            assertEquals(3, testCourses.size());
            checkCourse("CPSC", "221", "103",
                    "Basic Algorithms and Data Structures",
                    testSchedule1, 4, true, true, testCourses.get(0));
            checkCourse("CPSC", "213", "102",
                    "Introduction to Computer Systems",
                    testSchedule2, 4, true, false, testCourses.get(1));
            checkCourse("PSYC", "305A", "001",
                    "Personality Psychology",
                    testSchedule3, 3, false, false, testCourses.get(2));
        } catch (IOException | CourseAlreadyExistsException | CourseConflictsException e) {
            fail(FAIL_MSG_UEET);
        }
    }
}
