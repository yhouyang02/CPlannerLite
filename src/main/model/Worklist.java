package model;

import exception.CourseAlreadyExistsException;
import exception.CourseConflictsException;
import exception.CourseNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Represents a worklist to manage courses
public class Worklist implements Writable {

    // Recommended maximum courses in a worklist, for generating reminder
    public static final int COURSE_LIMIT = 5;

    private String name;
    private List<Course> courses;

    // EFFECTS: constructs a new worklist with name and an empty course list
    public Worklist(String name) {
        this.name = name;
        this.courses = new ArrayList<>();
    }

    // EFFECTS: returns name of this worklist
    public String getName() {
        return name;
    }

    // EFFECTS: returns list of courses of this worklist
    public List<Course> getCourses() {
        return courses;
    }

    // MODIFIES: this
    // EFFECTS: adds course to courses;
    //          throws CourseAlreadyExistsException if course is already existed in courses;
    //          throws CourseConflictsException if the schedule of course overlaps with any existing courses
    public void addCourse(Course course) throws CourseAlreadyExistsException, CourseConflictsException {
        if (courses.contains(course)) {
            throw new CourseAlreadyExistsException();
        } else {
            for (Course c : courses) {
                if (c.getSchedule().isOverlapping(course.getSchedule())) {
                    throw new CourseConflictsException();
                }
            }
        }
        courses.add(course);
    }

    // MODIFIES: this
    // EFFECTS: removes course from courses;
    //          throws CourseNotFoundException if course is not in courses
    public void deleteCourse(Course course) throws CourseNotFoundException {
        if (courses.isEmpty() || !courses.contains(course)) {
            throw new CourseNotFoundException();
        }
        courses.remove(course);
    }

    // MODIFIES: this
    // EFFECTS: sets course to required or not;
    //          throws CourseNotFoundException if courses does not contain course
    public void setRequired(Course course, boolean required) throws CourseNotFoundException {
        if (!courses.contains(course)) {
            throw new CourseNotFoundException();
        }
        courses.get(courses.indexOf(course)).setRequired(required);
    }

    // MODIFIES: this
    // EFFECTS: star course in courses;
    //          throws CourseNotFoundException if courses does not contain course;
    public void starCourse(Course course) throws CourseNotFoundException {
        if (!courses.contains(course)) {
            throw new CourseNotFoundException();
        }
        courses.get(courses.indexOf(course)).star();
    }

    // MODIFIES: this
    // EFFECTS: unstar course in courses;
    //          throws CourseNotFoundException if courses does not contain course;
    public void unstarCourse(Course course) throws CourseNotFoundException {
        if (!courses.contains(course)) {
            throw new CourseNotFoundException();
        }
        courses.get(courses.indexOf(course)).unstar();
    }

    // EFFECTS: returns a list of starred courses in courses
    public List<Course> getStarredCourses() {
        List<Course> starredCourses = new ArrayList<>();
        for (Course c : courses) {
            if (c.isStarred()) {
                starredCourses.add(c);
            }
        }
        return starredCourses;
    }

    // EFFECTS: returns a list of unstarred courses in courses
    public List<Course> getUnstarredCourses() {
        List<Course> starredCourses = new ArrayList<>();
        for (Course c : courses) {
            if (!c.isStarred()) {
                starredCourses.add(c);
            }
        }
        return starredCourses;
    }

    // EFFECTS: returns the total credits of all courses in courses
    public int getTotalCredits() {
        int credits = 0;
        for (Course c : courses) {
            credits += c.getCredits();
        }
        return credits;
    }

    // EFFECTS: returns a set of all subject codes appeared in courses
    public Set<String> getSubjectCodes() {
        Set<String> codes = new HashSet<>();
        for (Course c : courses) {
            codes.add(c.getSubjectCode());
        }
        return codes;
    }

    // EFFECTS: returns a set of subject, course, and section codes of all courses
    public Set<String> getAllCodes() {
        Set<String> codes = new HashSet<>();
        for (Course c : courses) {
            codes.add(c.getSubjectCourseCode() + " " + c.getSectionCode());
        }
        return codes;
    }

    // EFFECTS: returns a set of subject, course, and section codes of starred courses
    public Set<String> getStarredCodes() {
        Set<String> codes = new HashSet<>();
        for (Course c : getStarredCourses()) {
            codes.add(c.getSubjectCourseCode() + " " + c.getSectionCode());
        }
        return codes;
    }

    // EFFECTS: returns a set of subject, course, and section codes of unstarred courses
    public Set<String> getUnstarredCodes() {
        Set<String> codes = new HashSet<>();
        for (Course c : getUnstarredCourses()) {
            codes.add(c.getSubjectCourseCode() + " " + c.getSectionCode());
        }
        return codes;
    }

    // EFFECTS: returns the number of courses with the given subject code;
    //          returns 0 if there are no courses with the given subject code
    public int getNumCoursesOfSubject(String subject) {
        int num = 0;
        for (Course c : courses) {
            if (c.getSubjectCode().equals(subject)) {
                num++;
            }
        }
        return num;
    }

    // EFFECTS: returns the number of required courses in courses
    public int getNumRequiredCourses() {
        int num = 0;
        for (Course c : courses) {
            if (c.isRequired()) {
                num++;
            }
        }
        return num;
    }

    // EFFECTS: returns the number of non-required courses in courses
    public int getNumOptionalCourses() {
        int num = 0;
        for (Course c : courses) {
            if (!c.isRequired()) {
                num++;
            }
        }
        return num;
    }

    // EFFECTS: returns this worklist as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("courses", coursesToJson());
        return json;
    }

    // EFFECTS: returns courses in this worklist as a JSON array
    private JSONArray coursesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Course c : courses) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }

}
