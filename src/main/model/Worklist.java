package model;

import java.util.List;

// Represents a worklist to manage courses
public class Worklist {

    private String name;
    private List<Course> courses;

    // EFFECTS: constructs a new worklist with name
    public Worklist(String name) {
        // stub
    }

    public String getName() {
        return null; // stub
    }

    public List<Course> getCourses() {
        return null; // stub
    }

    // REQUIRES: c or other courses with the same code is not in this.courses
    // MODIFIES: this
    // EFFECTS: adds c to courses
    public void addCourse(Course c) {
        // stub
    }

    // REQUIRES: c is in this.courses
    // MODIFIES: this
    // EFFECTS: removes c from courses
    public void deleteCourse(Course c) {
        // stub
    }

    // REQUIRES: a course with getSubjectCourseCode().equals(code)
    // MODIFIES: this
    // EFFECTS:
    public void setRequired(String code) {
        // stub
    }

    // REQUIRES: code has to have format "<subjectCode> <courseCode>", and
    //           a course with getSubjectCourseCode().equals(code) is in this.courses
    // MODIFIES: this
    // EFFECTS: star the course with subject and course codes code in this.courses
    public void star(String code) {
        // stub
    }

    // REQUIRES: code has to have format "<subjectCode> <courseCode>", and
    //           a course with getSubjectCourseCode().equals(code) is in this.courses
    // MODIFIES: this
    // EFFECTS: unstar the course with subject and course codes code in this.courses
    public void unstar(String code) {
        // stub
    }

    // EFFECTS: returns a list of starred courses in this worklist
    public List<Course> getStarredCourses() {
        return null; // stub
    }

    // EFFECTS: returns the total credits of all courses in this.courses
    public int getTotalCredits() {
        return 0; // stub
    }

    // EFFECTS: returns the number of courses with the subject code in this.courses;
    //          if there are no courses with the given subject code, returns 0
    public int getNumSubject(String subject) {
        return 0; // stub
    }

    // EFFECTS: returns the number of required courses in this.courses
    public int getNumRequiredCourses() {
        return 0; // stub
    }

    // EFFECTS: returns the number of non-required courses in this.courses
    public int getNumOptionalCourses() {
        return 0; // stub
    }
}
