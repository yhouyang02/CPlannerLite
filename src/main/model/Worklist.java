package model;

import exception.CourseAlreadyExistsException;
import exception.CourseConflictsException;
import exception.CourseNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Represents a worklist to manage courses
public class Worklist {

    public static final int RECOMMENDED_COURSE_LIMIT = 5;

    private String name;
    private List<Course> courses;

    // EFFECTS: constructs a new worklist with name and an empty course list
    public Worklist(String name) {
        this.name = name;
        this.courses = new ArrayList<>();
    }

    // EFFECTS: returns the name of this worklist
    public String getName() {
        return name;
    }

    // EFFECTS: returns the list of courses of this worklist
    public List<Course> getCourses() {
        return courses;
    }

    // MODIFIES: this
    // EFFECTS: if course or other courses with the same code exists in courses, throws CourseAlreadyExistsException;
    //          if course has a overlapped schedule with any existing courses, throws CourseConflictsException;
    //          otherwise, adds course to courses
    public void addCourse(Course course) throws CourseAlreadyExistsException, CourseConflictsException {
        if (courses.contains(course)) {
            throw new CourseAlreadyExistsException();
        } else if (hasCourseWithCode(course.getSubjectCourseCode())) {
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
    // EFFECTS: if course is not in courses, throws CourseNotFoundException;
    //          otherwise, removes course from courses
    public void deleteCourse(Course course) throws CourseNotFoundException {
        if (courses.isEmpty() || !courses.contains(course)) {
            throw new CourseNotFoundException();
        }
        courses.remove(course);
    }

    // MODIFIES: this
    // EFFECTS: if no course with getSubjectCourseCode() equals code, throws CourseNotFoundException;
    //          otherwise, sets the course with the given code to required or not
    public void setRequired(String code, boolean required) throws CourseNotFoundException {
        if (!hasCourseWithCode(code)) {
            throw new CourseNotFoundException();
        }
        getCourseWithCode(code).setRequired(required);
    }

    // MODIFIES: this
    // EFFECTS: if no course with getSubjectCourseCode() equals code exists, throws CourseNotFoundException;
    //          otherwise, star the course with subject and course codes code in this.courses
    public void star(String code) throws CourseNotFoundException {
        if (!hasCourseWithCode(code)) {
            throw new CourseNotFoundException();
        }
        getCourseWithCode(code).star();
    }

    // MODIFIES: this
    // EFFECTS: if no course with getSubjectCourseCode() equals code exists, throws CourseNotFoundException;
    //          otherwise, unstar the course with subject and course codes code in this.courses
    public void unstar(String code) throws CourseNotFoundException {
        if (!hasCourseWithCode(code)) {
            throw new CourseNotFoundException();
        }
        getCourseWithCode(code).unstar();
    }

    // EFFECTS: returns true if there exists a course with getSubjectCourseCode(), false otherwise
    private boolean hasCourseWithCode(String code) {
        for (Course c : courses) {
            if (c.getSubjectCourseCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: there exists a course with getSubjectCourseCode() equals code in courses;
    // EFFECTS: returns the course object with subject and course codes code
    private Course getCourseWithCode(String code) {
        Course course = null;
        for (Course c : courses) {
            if (c.getSubjectCourseCode().equals(code)) {
                course = c;
            }
        }
        return course;
    }

    // EFFECTS: returns a list of starred courses in this worklist
    public List<Course> getStarredCourses() {
        List<Course> starredCourses = new ArrayList<>();
        for (Course c : courses) {
            if (c.isStarred()) {
                starredCourses.add(c);
            }
        }
        return starredCourses;
    }

    // EFFECTS: returns the total credits of all courses in this.courses
    public int getTotalCredits() {
        int credits = 0;
        for (Course c : courses) {
            credits += c.getCredits();
        }
        return credits;
    }

    // EFFECTS: returns a set of all subject code appeared in courses
    public Set<String> getSubjectCodes() {
        Set<String> codes = new HashSet<>();
        for (Course c : courses) {
            codes.add(c.getSubjectCode());
        }
        return codes;
    }

    // EFFECTS: if there are no courses with the given subject code, returns 0;
    //          otherwise, returns the number of courses with the subject code in courses
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
    
}
