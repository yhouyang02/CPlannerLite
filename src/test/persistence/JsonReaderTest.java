package persistence;

import model.Course;
import model.Worklist;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// NOTE: This class is adjusted from JsonReaderTest.java; Link below:
//       https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/test/persistence/JsonReaderTest.java
public class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        try {
            JsonReader reader = new JsonReader("./data/noSuchFile.json");
            Worklist testWorklist = reader.read();
            fail(FAIL_MSG_EENT);
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void testReaderInvalidWorklist() {
        try {
            JsonReader reader = new JsonReader("./data/testReaderInvalidWorklist.json");
            Worklist testWorklist = reader.read();
            fail(FAIL_MSG_EENT);
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReaderEmptyWorklist() {
        try {
            JsonReader reader = new JsonReader("./data/testReaderEmptyWorklist.json");
            Worklist testWorklist = reader.read();
            assertEquals("Test Worklist", testWorklist.getName());
            assertEquals(0, testWorklist.getCourses().size());
        } catch (IOException e) {
            fail(FAIL_MSG_UEET);
        }
    }

    @Test
    void testReaderGeneralWorklist() {
        try {
            initSchedules();
            JsonReader reader = new JsonReader("./data/testReaderGeneralWorklist.json");
            Worklist testWorklist = reader.read();
            assertEquals("Test Worklist", testWorklist.getName());
            List<Course> testCourses = testWorklist.getCourses();
            assertEquals(3, testCourses.size());
            checkCourse("CHEM", "208", "112",
                    "Coordination Chemistry",
                    testSchedule1, 3, true, true, testCourses.get(0));
            checkCourse("KIN", "140", "002",
                    "Lifespan Motor Development",
                    testSchedule2, 3, false, true, testCourses.get(1));
            checkCourse("PHYS", "200", "101",
                    "Relativity and Quanta",
                    testSchedule3, 4, true, false, testCourses.get(2));
        } catch (IOException e) {
            fail(FAIL_MSG_UEET);
        }
    }

}
