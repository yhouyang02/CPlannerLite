package persistence;

import model.Course;
import model.Schedule;
import model.Time;
import model.Worklist;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads worklist from JSON data stored in file
// NOTE: This class is adjusted from JsonReader.java; Link below:
//       https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonReader.java
public class JsonReader {

    private String source;

    // EFFECTS: constructs a JSON reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads worklist from file and returns it;
    //          throws IOException if an error occurs when reading data from file
    public Worklist read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorklist(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it;
    //          throws IOException if an error occurs when reading data from file
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses worklist from JSON object and returns it;
    //          throws IOException if an error occurs when reading data from file
    private Worklist parseWorklist(JSONObject jsonObject) throws IOException {
        String name = jsonObject.getString("name");
        Worklist wl = new Worklist(name);
        addCourses(wl, jsonObject);
        return wl;
    }

    // MODIFIES: wl
    // EFFECTS: parses courses from JSON object and adds them to worklist;
    //          throws IOException if an error occurs when reading data from file
    private void addCourses(Worklist wl, JSONObject jsonObject) throws IOException {
        JSONArray jsonArray = jsonObject.getJSONArray("courses");
        for (Object json : jsonArray) {
            JSONObject nextCourse = (JSONObject) json;
            addCourse(wl, nextCourse);
        }
    }

    // MODIFIES: wl
    // EFFECTS: parses course from JSON object and adds it to worklist;
    //          throws IOException if an error occurs when reading data from file
    private void addCourse(Worklist wl, JSONObject jsonObject) throws IOException {
        try {
            String subjectCode = jsonObject.getString("subjectCode");
            String courseCode = jsonObject.getString("courseCode");
            String sectionCode = jsonObject.getString("sectionCode");
            String title = jsonObject.getString("title");

            boolean[] days = new boolean[5];
            for (int i = 0; i < 5; i++) {
                days[i] = jsonObject.getJSONObject("schedule").getJSONArray("days").getBoolean(i);
            }
            Time startTime = new Time(jsonObject.getJSONObject("schedule").getInt("startHour"),
                    jsonObject.getJSONObject("schedule").getInt("startMinute"));
            Time endTime = new Time(jsonObject.getJSONObject("schedule").getInt("endHour"),
                    jsonObject.getJSONObject("schedule").getInt("endMinute"));

            Schedule schedule = new Schedule(days, startTime, endTime);
            int credits = jsonObject.getInt("credits");
            boolean required = jsonObject.getBoolean("required");
            boolean starred = jsonObject.getBoolean("starred");

            Course course = new Course(subjectCode, courseCode, sectionCode,
                    title, schedule, credits, required, starred);
            wl.addCourse(course);
        } catch (Exception e) {
            throw new IOException();
        }
    }

}
